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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import java.text.FieldPosition


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

    lateinit var danXuanTis: List<String>
    lateinit var panDuanTis: List<String>
    lateinit var duoXuanTis: List<String>

    lateinit var currentResult: String

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var thisDrawer:Drawer

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

        thisDrawer = DrawerBuilder()
                .withActivity(this)
                .addDrawerItems()
                .withOnDrawerItemClickListener { view, position, drawerItem ->false
                }
                .build()
    }

    fun initTi() {
        danXuanTis = readFileLines(R.raw.single_xt)
        panDuanTis = readFileLines(R.raw.single_xt)
        duoXuanTis = readFileLines(R.raw.single_xt)
    }

    fun click0(num: Int=Random().nextInt(danXuanTis.size)) {
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
        thisDrawer.removeAllItems()
        thisDrawer.addItems(DividerDrawerItem(),DividerDrawerItem(),DividerDrawerItem(),DividerDrawerItem())
        for (i in 0..200){
            val ret=sp.getInt("$i",0)
            if(ret>0){
                val item=PrimaryDrawerItem().withIdentifier((i+1).toLong()).withName("${i+1}"+danXuanTis[i].toTi().title)
                item.mOnDrawerItemClickListener= Drawer.OnDrawerItemClickListener { view, position, drawerItem ->
                    click0(i)
                    false
                }
                thisDrawer.addItem(item)
            }
        }

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
