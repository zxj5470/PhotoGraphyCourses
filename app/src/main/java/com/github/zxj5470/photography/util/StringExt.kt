package com.github.zxj5470.photography.util

/**
 * Created by zh on 2017/11/17.
 */
data class SingleXT(val id:Int,
                    val title: String,
                    val A: String,
                    val B: String,
                    val C: String,
                    val D: String,
                    val result: String)

fun String.toTi(): SingleXT {
    val s = this.split(",")
    return SingleXT(s[0].toInt(), s[1], s[2], s[3], s[4], s[5],s[6])
}