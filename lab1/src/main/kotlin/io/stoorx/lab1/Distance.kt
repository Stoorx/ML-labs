package io.stoorx.lab1

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


interface Distance {
    object Manhattan : Distance {
        override fun invoke(x: List<Double>, y: List<Double>): Double =
            x.zip(y) { a, b -> abs(a - b) }.sum()
    }

    object Euclidean : Distance {
        override fun invoke(x: List<Double>, y: List<Double>): Double =
            sqrt(x.zip(y) { a, b -> (a - b).pow(2) }.sum())
    }

    object Chebyshev : Distance {
        override fun invoke(x: List<Double>, y: List<Double>): Double =
            x.zip(y) { a, b -> abs(a - b) }.max() ?: Double.NaN
    }

    operator fun invoke(x: List<Double>, y: List<Double>): Double

    companion object {
        fun getAll(): List<Distance> = listOf(
            Manhattan,
            Euclidean,
            Chebyshev
        )
    }
}