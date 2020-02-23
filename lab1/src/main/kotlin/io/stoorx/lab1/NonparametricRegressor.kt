package io.stoorx.lab1

object NonparametricRegressor {
    fun predict(dataSet: DataSet, distance: Distance, window: Window, kernel: Kernel, query: List<Double>) =
        (dataSet.takeIf { it.lines.isNotEmpty() } ?: throw Exception("Empty dataset given"))
            .lines
            .map {
                it to distance(it.x, query)
            }
            .sortedBy { it.second }
            .let { sorted ->
                sorted.map {
                    it.first to kernel(window(it.second, sorted))
                }
            }
            .map {
                it.first.y.map { classWeight -> classWeight * it.second }
            }
            .reduce { acc: List<Double>, list: List<Double> ->
                acc.zip(list) { a, b -> a + b }
            }
            .mapIndexed { index, d -> index to d }
            .maxBy { it.second }!!
            .first
}