package io.stoorx.lab1

data class DataLine(
    val x: List<Double>,
    val y: List<Double>
) {
    val size: Int
        get() = x.size + y.size

    fun normalize(n: List<Double>): DataLine =
        if (n.size != x.size) throw Exception("Normalization error. Vectors have different size")
        else DataLine(x.zip(n) { xv, nv -> xv / nv }, y)

    fun dropFeature(idx: Int): DataLine =
        if (idx > x.lastIndex) throw Exception("DropFeature error. Index of feature out of feature set")
        else DataLine(x.toMutableList().apply { removeAt(idx) }, y)
}