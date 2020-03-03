package io.stoorx.lab2

import java.io.File

fun main() {
    val datasetName = "1.txt"
    val input = File(datasetName).bufferedReader()

    val featureCount = readLine()!!.toInt()
    val trainSetSize = readLine()!!.toInt()
    val trainSet = ArrayList<Array<Int>>(trainSetSize)
    for (i in 0 until trainSetSize) {
        trainSet.add(readLine()!!.split(' ').map { it.toInt() }.toTypedArray())
    }
    val testSetSize = readLine()!!.toInt()
    val testSet = ArrayList<Array<Int>>(trainSetSize)
    for (i in 0 until testSetSize) {
        testSet.add(readLine()!!.split(' ').map { it.toInt() }.toTypedArray())
    }


}