package com.github.zxj5470.photography

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewManager
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import android.content.SharedPreferences
import com.github.zxj5470.android_kotlin_ext.ui.view.visible
import com.github.zxj5470.photography.util.*
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem


class MainActivity : AppCompatActivity() {

    enum class PGStatus {
        Dan, Duo, Pan
    }

    var currentStatus: PGStatus = PGStatus.Dan


    lateinit var titleText: TextView
    lateinit var AText: TextView
    lateinit var BText: TextView
    lateinit var CText: TextView
    lateinit var DText: TextView
    lateinit var contentText: TextView
    lateinit var infoText: TextView
    lateinit var singleSample: SingleXT
    lateinit var trueOrFalseSample: PanDuanTi

    var currentPage: Int = 0
    var totalNumber: Int = 0

    lateinit var danXuanTis: List<String>
    lateinit var panDuanTis: List<String>
    lateinit var duoXuanTis: List<String>

    lateinit var currentResult: String

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var thisDrawer: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShared()
        init()
        initTi()
        click0(200)
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
        verticalLayout {
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
                when (currentStatus) {
                    PGStatus.Dan -> {
                        val num = sp.getInt((singleSample.id - 1).toString(), 0)
                        editor = sp.edit()
                        editor.putInt((singleSample.id - 1).toString(), num + 1)
                        editor.commit()

                        contentText.text = "回答正确"
                        contentText.textSize = 22f
                        contentText.setTextColor(green)
                        contentText.visible = true
                        val times = sp.getInt((singleSample.id - 1).toString(), 0)
                        infoText.text = "题目：${singleSample.id}/${danXuanTis.size},已做过 $times 次"
                    }
                    PGStatus.Pan -> {
                        val num = sp.getInt((trueOrFalseSample.id - 1).toString(), 0)
                        editor = sp.edit()
                        editor.putInt((trueOrFalseSample.id - 1).toString(), num + 1)
                        editor.commit()

                        contentText.text = "回答正确"
                        contentText.textSize = 22f
                        contentText.setTextColor(green)
                        contentText.visible = true
                        val times = sp.getInt((trueOrFalseSample.id - 1).toString(), 0)
                        infoText.text = "题目：${trueOrFalseSample.id}/${panDuanTis.size},已做过 $times 次"
                    }
                    else -> {
                    }
                }

            }


            AText.setOnClickListener {
                if (currentResult == "A" || currentResult == "√") {
                    setColorBack()
                    AText.setTextColor(green)
                    f()
                } else {
                    AText.setTextColor(red)
                    elseF()
                }
            }
            BText.setOnClickListener {
                if (currentResult == "B" || currentResult == "×") {
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
                .build()
    }

    fun initTi() {
        danXuanTis = readFileLines(R.raw.single_xt)
        panDuanTis = readFileLines(R.raw.single_xt)
        duoXuanTis = readFileLines(R.raw.single_xt)
        totalNumber = danXuanTis.size + panDuanTis.size

    }

    fun click0(num: Int = Random().nextInt(totalNumber)) {
        setColorBack()

        if (num < danXuanTis.size) {

            currentStatus = PGStatus.Dan
            singleSample = danXuanTis[num].toTi()

            titleText.text = "题目 ${singleSample.id} : " + singleSample.title
            AText.text = "A. " + singleSample.A
            BText.text = "B. " + singleSample.B
            CText.text = "C. " + singleSample.C
            DText.text = "D. " + singleSample.D

            val times = sp.getInt((singleSample.id - 1).toString(), 0)
            infoText.text = "题目：${singleSample.id}/$totalNumber,已做过 $times 次"
            currentResult = singleSample.result


        } else {
            currentStatus = PGStatus.Pan
            trueOrFalseSample = panDuanTis[num - danXuanTis.size].toPanDuanTi()

            titleText.text = "题目 ${trueOrFalseSample.id} : " + trueOrFalseSample.title
            AText.text = "√"
            BText.text = "×"
            CText.text = ""
            DText.text = ""

            val times = sp.getInt((trueOrFalseSample.id - 1).toString(), 0)
            infoText.text = "题目：${trueOrFalseSample.id}/${panDuanTis.size},已做过 $times 次"
            currentResult = trueOrFalseSample.result
            currentPage = 0
        }

        println("$num,是什么题目？$currentStatus")

        thisDrawer.removeAllItems()
        var temp: SingleXT
        thisDrawer.addItems(DividerDrawerItem(), DividerDrawerItem(), DividerDrawerItem(), DividerDrawerItem())
        for (i in 0..danXuanTis.size) {
            val ret = sp.getInt("$i", 0)
            if (ret > 0) {
                temp = danXuanTis[i].toTi()
                val item = PrimaryDrawerItem().withIdentifier(i.toLong()).withName("${temp.id}:${temp.title}")
                item.mOnDrawerItemClickListener = Drawer.OnDrawerItemClickListener { view, position, drawerItem ->
                    click0(drawerItem.identifier.toInt())
                    false
                }
                thisDrawer.addItem(item)
            }
        }

        var tp:PanDuanTi
        for (i in 0..panDuanTis.size) {
            val ret = sp.getInt("${danXuanTis.size+i}", 0)
            if (ret > 0) {
                tp = panDuanTis[i].toPanDuanTi()
                val item = PrimaryDrawerItem().withIdentifier((i + danXuanTis.size).toLong()).withName("${tp.id}:${tp.title}")
                item.mOnDrawerItemClickListener = Drawer.OnDrawerItemClickListener { view, position, drawerItem ->
                    click0(drawerItem.identifier.toInt())
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
