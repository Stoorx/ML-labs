package io.stoorx.lab2.katrix

import io.stoorx.lab2.katrix.exceptions.MatrixSizeException

class MutableMatrix : Matrix {
    /**
     * Constructors
     */
    constructor(rows: Int, columns: Int, init: (row: Int, col: Int) -> Double)
            : super(rows, columns, init)

    constructor(packed: List<Double>, rows: Int) : super(packed, rows)

    constructor(rows: Int, columns: Int, scalar: Double) : super(rows, columns, scalar)

    private constructor(packed: ArrayList<Double>, rows: Int, columns: Int) : super(packed, rows, columns)

    /**
     * Access operators
     */
    operator fun set(row: Int, column: Int, element: Double) {
        data[toLinearIndex(row, column)] = element
    }

    /**
     * Matrix operators
     */
    operator fun plusAssign(other: Matrix) {
        if (checkDimensions(other))
            data.forEachIndexed { index, _ -> data[index] += other.packedData[index] }
        else throw MatrixSizeException()
    }

    operator fun minusAssign(other: Matrix) {
        if (checkDimensions(other))
            data.forEachIndexed { index, _ -> data[index] -= other.packedData[index] }
        else throw MatrixSizeException()
    }

    operator fun timesAssign(other: Matrix) {
        if (checkDimensions(other))
            data.forEachIndexed { index, _ -> data[index] *= other.packedData[index] }
        else throw MatrixSizeException()
    }

    operator fun divAssign(other: Matrix) {
        if (checkDimensions(other))
            data.forEachIndexed { index, _ -> data[index] /= other.packedData[index] }
        else throw MatrixSizeException()
    }


    /**
     * Scalar operators
     */
    operator fun plusAssign(scalar: Double) {
        data.forEachIndexed { index, _ -> data[index] += scalar }
    }

    operator fun minusAssign(scalar: Double) {
        data.forEachIndexed { index, _ -> data[index] -= scalar }
    }

    operator fun timesAssign(scalar: Double) {
        data.forEachIndexed { index, _ -> data[index] *= scalar }
    }

    operator fun divAssign(scalar: Double) {
        data.forEachIndexed { index, _ -> data[index] /= scalar }
    }
}

fun Matrix.toMutableMatrix() = MutableMatrix(packedData, rows)