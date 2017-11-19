package com.github.zxj5470.photography.util

/**
 * Created by zh on 2017/11/17.
 */

fun String.toTi(): SingleXT {
    val s = this.split(",")
    return SingleXT(s[0].toInt(), s[1], s[2], s[3], s[4], s[5],s[6])
}

fun String.toPanDuanTi(): PanDuanTi {
    val s = this.split(",")
    return PanDuanTi(s[0].toInt(), s[1], s[2])
}