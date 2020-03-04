package io.stoorx.lab2.katrix.transformations

import io.stoorx.lab2.katrix.Matrix

data class SingularValueDecomposition(
    val U: Matrix,
    val D: Matrix,
    val V: Matrix
)

fun Matrix.singularValueDecomposition() {

}