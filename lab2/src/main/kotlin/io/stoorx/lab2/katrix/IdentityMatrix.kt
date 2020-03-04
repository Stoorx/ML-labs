package io.stoorx.lab2.katrix

fun identityMatrix(rows: Int, columns: Int) =
    MutableMatrix(rows, columns) { row, col ->
        if (row == col) 1.0
        else 0.0
    }

fun identityMatrix(size: Int) = identityMatrix(size, size)