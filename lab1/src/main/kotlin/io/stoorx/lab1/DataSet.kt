package io.stoorx.lab1

import kotlin.math.abs
import kotlin.math.max

data class DataSet(
    val header: List<String>,
    val lines: Collection<DataLine>
) {
    val width: Int =
        if (lines.any { it.size != header.size })
            throw Exception("Different size of lines and header")
        else header.size

    fun normalize(): DataSet =
        lines.fold(List<Double>(width - 1) { lines.first().x[it] }) { acc, dataLine ->
            acc.zip(dataLine.x) { a, b -> max(abs(a), abs(b)) }
        }.let { n ->
            DataSet(header, lines.map { it.normalize(n) })
        }

    fun removeFeature(name: String): DataSet =
        header.indexOf(name).let { idx ->
            DataSet(
                header.toMutableList().apply { removeAt(idx) },
                lines.map { it.dropFeature(idx) }
            )
        }

    fun shuffled(): DataSet = DataSet(header, lines.shuffled())
}