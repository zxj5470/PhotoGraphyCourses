package com.github.zxj5470.photography

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import com.github.zxj5470.photography.util.readFileLines
import com.github.zxj5470.photography.util.toTi
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import android.R.id.edit
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.github.zxj5470.android_kotlin_ext.ui.view.visible
import com.github.zxj5470.photography.util.SingleXT


class MainActivity : AppCompatActivity() {

    lateinit var titleText: TextView
    lateinit var AText: TextView
    lateinit var BText: TextView
    lateinit var CText: TextView
    lateinit var DText: TextView
    lateinit var contentText: TextView
    lateinit var infoText: TextView
    lateinit var sample: SingleXT

    var currentPage: Int = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                click0()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                click1()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                click2()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    lateinit var danXuanTis: List<String>
    lateinit var panDuanTis: List<String>
    lateinit var duoXuanTis: List<String>

    lateinit var currentResult: String

    lateinit var navigation: BottomNavigationView

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShared()
        init()
        initTi()
        click0()
    }

    private fun initShared() {
        sp = getSharedPreferences("conf", MODE_PRIVATE)
    }

    fun ViewManager.textView50(): android.widget.TextView {
        return textView {
            topPadding = 80
            textSize = 20f
        }
    }


    val red = Color.rgb(255, 99, 71)
    val green = Color.rgb(0, 238, 118)
    fun setColorBack() {
        val gray = Color.GRAY
        AText.setTextColor(gray)
        BText.setTextColor(gray)
        CText.setTextColor(gray)
        DText.setTextColor(gray)
    }

    fun init() {
        val a = verticalLayout {

            padding = dip(30)
            titleText = textView50()
            AText = textView50()
            BText = textView50()
            CText = textView50()
            DText = textView50()

            contentText = textView50()
            infoText = textView50()
            button("随机下一题") {
                onClick {
                    click0()
                }
            }

            fun f() {
                val num = sp.getInt(sample.id.toString(), 0)
                editor = sp.edit()
                editor.putInt(sample.id.toString(), num + 1)
                editor.commit()


                contentText.text = "回答正确"
                contentText.textSize = 22f
                contentText.setTextColor(green)
                contentText.visible = true
                val times = sp.getInt(sample.id.toString(), 0)
                infoText.text = "题目：${sample.id}/${danXuanTis.size},已做过 $times 次"
            }


            AText.setOnClickListener {
                if (currentResult == "A") {
                    setColorBack()
                    AText.setTextColor(green)
                    f()
                } else {
                    AText.setTextColor(red)
                    elseF()
                }
            }
            BText.setOnClickListener {
                if (currentResult == "B") {
                    setColorBack()
                    f()
                    BText.setTextColor(green)
                } else {
                    BText.setTextColor(red)
                    elseF()
                }
            }
            CText.setOnClickListener {
                if (currentResult == "C") {
                    setColorBack()
                    f()
                    CText.setTextColor(green)
                } else {
                    CText.setTextColor(red)
                    elseF()
                }
            }
            DText.setOnClickListener {
                if (currentResult == "D") {
                    setColorBack()
                    f()
                    DText.setTextColor(green)
                } else {
                    DText.setTextColor(red)
                    elseF()
                }
            }
        }
        val bottom = BottomNavigationView(this@MainActivity)
        val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.gravity = Gravity.BOTTOM
        a.addView(bottom, param)
        bottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun initTi() {
        danXuanTis = readFileLines(R.raw.single_xt)
        panDuanTis = readFileLines(R.raw.single_xt)
        duoXuanTis = readFileLines(R.raw.single_xt)
    }

    fun click0() {
        val num = Random().nextInt(danXuanTis.size)
        setColorBack()
        sample = danXuanTis[num].toTi()

        titleText.text = "题目 ${sample.id} : " + sample.title

        AText.text = "A. " + sample.A
        BText.text = "B. " + sample.B
        CText.text = "C. " + sample.C
        DText.text = "D. " + sample.D

        val times = sp.getInt(sample.id.toString(), 0)

        infoText.text = "题目：${sample.id}/${danXuanTis.size},已做过 $times 次"

        currentResult = sample.result
        currentPage = 0
    }


    fun elseF() {
        contentText.visible = false
    }

    fun click1() {
        currentPage = 1
    }

    fun click2() {
        currentPage = 2
    }
}
