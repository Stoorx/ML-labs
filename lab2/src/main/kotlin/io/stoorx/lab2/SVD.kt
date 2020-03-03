package io.stoorx.lab2

import kotlin.math.pow

fun Matrix.singularValueDecomposition(stopCondition: Double) {

}

private fun Matrix.squaredError(a: Array<Double>, b: Array<Double>) =
    if (this.height == b.size && this.width == a.size)
        (0 until this.height).fold(0.0) { acc, i ->
            acc + (0 until this.width).fold(0.0) { accRow, j ->
                accRow + this[i, j] - b[i] * a[j]
            }
        } / 2
    else throw Exception("Matrices have incompatible sizes")

private fun Matrix.vectorA(b: Array<Double>): Array<Double> =
    Array(this.width) { j ->
        (0 until this.height).fold(0.0) { acc, i ->
            acc + this[i, j] * b[i]
        } / b.sumByDouble { it.pow(2) }
    }

private fun Matrix.vectorB(a: Array<Double>): Array<Double> =
    Array(this.height) { i ->
        (0 until this.width).fold(0.0) { acc, j ->
            acc + this[i, j] * a[j]
        } / a.sumByDouble { it.pow(2) }
    }
