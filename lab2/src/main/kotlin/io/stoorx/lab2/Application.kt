package io.stoorx.lab2

import io.stoorx.lab2.katrix.transformations.qrDecomposition

fun main() {
//    val datasetName = "1.txt"
//    val input = File(datasetName).bufferedReader()
//
//    val featureCount = readLine()!!.toInt()
//    val trainSetSize = readLine()!!.toInt()
//    val trainSet = ArrayList<Array<Int>>(trainSetSize)
//    for (i in 0 until trainSetSize) {
//        trainSet.add(readLine()!!.split(' ').map { it.toInt() }.toTypedArray())
//    }
//    val testSetSize = readLine()!!.toInt()
//    val testSet = ArrayList<Array<Int>>(trainSetSize)
//    for (i in 0 until testSetSize) {
//        testSet.add(readLine()!!.split(' ').map { it.toInt() }.toTypedArray())
//    }

//    val svd: SingularValueDecomposition=
//        Matrix(arrayOf(
//            doubleArrayOf(1.0, 0.0, 0.0, 0.0, 2.0),
//            doubleArrayOf(0.0, 0.0, 3.0, 0.0, 0.0),
//            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0),
//            doubleArrayOf(0.0, 4.0, 0.0, 0.0, 0.0)
//        )).svd()
//
//    println(svd.s.toString())
//    println(svd.v.toString())
//    println(svd.u.toString())
    val m = io.stoorx.lab2.katrix.Matrix(
        arrayListOf(
            1.0, 2.0, 3.0,
            3.0, 4.0, 5.0,
            5.0, 6.0, 7.0,
            7.0, 8.0, 9.0
        ), 4
    )
    val qr = m.qrDecomposition()

    println("a")
}