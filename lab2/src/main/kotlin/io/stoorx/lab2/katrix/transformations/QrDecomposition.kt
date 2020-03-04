package io.stoorx.lab2.katrix.transformations

import io.stoorx.lab2.katrix.Matrix
import io.stoorx.lab2.katrix.identityMatrix
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


data class QrDecomposition(
    val Q: Matrix,
    val R: Matrix
)

fun Matrix.qrDecomposition(): QrDecomposition {
    var workingMatrix = if (this.columns > this.rows) this.transpose() else this.copy()
    var Q: Matrix = identityMatrix(workingMatrix.rows, workingMatrix.rows)

    for (i in 0 until workingMatrix.rows) {
        for (j in i + 1 until workingMatrix.rows) {
            val angle = atan(workingMatrix[j, i] / workingMatrix[i, i])
            val g = givensMatrix(workingMatrix.rows, i, j, angle)
            Q = Q multiply g
            workingMatrix = g multiply workingMatrix
        }
    }

    return QrDecomposition(
        Q,
        workingMatrix
    )
}

fun givensMatrix(rows: Int, i: Int, j: Int, angle: Double) =
    with(Pair(cos(angle), sin(angle))) {
        Matrix(rows, rows) { row, col ->
            if (row == col)
                if (row == i || row == j)
                    this.first
                else 1.0
            else if (row == i && col == j)
                second
            else if (row == j && col == i)
                -second
            else 0.0
        }
    }