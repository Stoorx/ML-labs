package io.stoorx.lab2.katrix

import io.stoorx.lab2.katrix.exceptions.MatrixSizeException

open class Matrix protected constructor(
    val rows: Int,
    val columns: Int
) {
    /**
     * Constructors
     */
    constructor(rows: Int, columns: Int, init: (row: Int, col: Int) -> Double) : this(rows, columns) {
        data = ArrayList<Double>(rows * columns).apply {
            (0 until rows).forEach { row ->
                (0 until columns).forEach { col -> add(init(row, col)) }
            }
        }
    }

    constructor(packed: List<Double>, rows: Int) : this(rows, if (rows != 0) packed.size / rows else 0) {
        data = if (rows * columns != packed.size)
            throw MatrixSizeException()
        else ArrayList<Double>(rows * columns).apply { addAll(packed) }
    }

    constructor(rows: Int, columns: Int, scalar: Double) : this(rows, columns) {
        data = ArrayList<Double>(rows * columns).apply {
            (0 until rows * columns).forEach { _ -> add(scalar) }
        }
    }

    protected constructor(packed: ArrayList<Double>, rows: Int, columns: Int) : this(rows, columns) {
        data = packed
    }

    /**
     *  Public methods
     */
    fun transpose() = Matrix(columns, rows) { row, col -> this[col, row] }

    fun copy() = Matrix(data, rows)

    override fun equals(other: Any?): Boolean {
        return (other as? Matrix)
            ?.let {
                this.rows == other.rows && this.columns == other.columns &&
                        this.data == other.packedData
            } ?: false
    }

    override fun hashCode(): Int {
        var hash = 1
        data.forEach { hash = (31.0 * it).toInt() + hash * 71 }
        return hash
    }

    protected lateinit var data: ArrayList<Double>

    val packedData: List<Double>
        get() = data


    protected fun toLinearIndex(row: Int, column: Int): Int = row * columns + column
    protected fun checkDimensions(other: Matrix): Boolean = rows == other.rows && columns == other.columns

    /**
     * Access operators
     */
    operator fun get(row: Int, column: Int): Double =
        if (row < rows && column < columns && row >= 0 && column >= 0)
            data[toLinearIndex(row, column)]
        else throw IndexOutOfBoundsException()

    /**
     * Matrix operators
     */
    operator fun plus(other: Matrix) =
        if (checkDimensions(other))
            Matrix(data.zip(other.data) { a, b -> a + b }.let {
                it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
            }, rows, columns)
        else throw MatrixSizeException()

    operator fun minus(other: Matrix) =
        if (checkDimensions(other))
            Matrix(data.zip(other.data) { a, b -> a - b }.let {
                it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
            }, rows, columns)
        else throw MatrixSizeException()

    operator fun times(other: Matrix) =
        if (checkDimensions(other))
            Matrix(data.zip(other.data) { a, b -> a * b }.let {
                it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
            }, rows, columns)
        else throw MatrixSizeException()

    operator fun div(other: Matrix) =
        if (checkDimensions(other))
            Matrix(data.zip(other.data) { a, b -> a / b }.let {
                it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
            }, rows, columns)
        else throw MatrixSizeException()

    infix fun multiply(other: Matrix) =
        if (this.columns == other.rows)
            Matrix(rows, other.columns) { row, col ->
                (0 until this.columns).fold(0.0) { acc, i ->
                    acc + this[row, i] * other[i, col]
                }
            }
        else throw MatrixSizeException()

    /**
     * Scalar operators
     */
    operator fun plus(scalar: Double) =
        Matrix(data.map { it + scalar }.let {
            it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
        }, rows, columns)

    operator fun minus(scalar: Double) =
        Matrix(data.map { it - scalar }.let {
            it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
        }, rows, columns)

    operator fun times(scalar: Double) =
        Matrix(data.map { it * scalar }.let {
            it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
        }, rows, columns)

    operator fun div(scalar: Double) =
        Matrix(data.map { it / scalar }.let {
            it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
        }, rows, columns)

    operator fun unaryMinus() =
        Matrix(data.map { -it }.let {
            it as? ArrayList<Double> ?: ArrayList(it) // To avoid copying. Actually type cast is always succeeded.
        }, rows, columns)
}

