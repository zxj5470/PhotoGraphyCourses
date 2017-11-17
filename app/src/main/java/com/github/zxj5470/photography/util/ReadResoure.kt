package com.github.zxj5470.photography.util

import com.github.zxj5470.photography.MainActivity
import com.github.zxj5470.photography.R

/**
 * Created by zh on 2017/11/17.
 */
fun MainActivity.readFileLines(csvID:Int)=this.resources.openRawResource(csvID).bufferedReader().readLines()

