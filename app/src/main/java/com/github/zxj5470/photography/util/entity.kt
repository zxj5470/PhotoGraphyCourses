package com.github.zxj5470.photography.util

/**
 * Created by zh on 2017/11/19.
 */
open class Ti
data class SingleXT(val id:Int,
                    val title: String,
                    val A: String,
                    val B: String,
                    val C: String,
                    val D: String,
                    val result: String):Ti()

data class PanDuanTi(val id:Int,
                     val title: String,
                     val result: String
                     ):Ti()