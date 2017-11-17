package com.github.zxj5470.photography.util

import com.github.zxj5470.photography.MainActivity

/**
 * Created by zh on 2017/11/17.
 */
fun MainActivity.readLFileLines(csv:String)=this.resources.assets.open(csv).bufferedReader().readLines()

