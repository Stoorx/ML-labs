package io.stoorx.lab2

import kotlin.math.pow
import kotlin.math.sqrt

class Matrix(val height: Int, val width: Int, init: (row: Int, col: Int) -> Double) {
    val data: Array<Array<Double>> = Array(height) { row ->
        Array(width) { col -> init(row, col) }
    }

    operator fun get(row: Int, col: Int): Double = data[row][col]
    operator fun set(row: Int, col: Int, value: Double) {
        data[row][col] = value
    }

    fun transpose(): Matrix =
        Matrix(width, height) { row, col -> this[col, row] }
}

operator fun Matrix.times(other: Matrix) =
    if (this.width == other.height)
        Matrix(height, other.width) { row, col ->
            (0 until this.width).fold(0.0) { acc, i ->
                acc + this[row, i] * other[i, col]
            }
        }
    else throw Exception("Matrices have incompatible sizes")

operator fun Matrix.minus(other: Matrix) =
    if (this.width == other.width && this.height == other.height)
        Matrix(height, width) { row, col ->
            this[row, col] - other[row, col]
        }
    else throw Exception("Matrices have incompatible sizes")

operator fun Matrix.plus(other: Matrix) =
    if (this.width == other.width && this.height == other.height)
        Matrix(height, width) { row, col ->
            this[row, col] + other[row, col]
        }
    else throw Exception("Matrices have incompatible sizes")

fun Matrix.moduloOfRow(row: Int) =
    sqrt(
        (0 until this.width).fold(0.0) { acc, i ->
            acc + this[row, i].pow(2)
        }
    )

