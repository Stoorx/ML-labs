package io.stoorx.lab1

object LeaveOneOutValidator {
    fun getConfusionMatrix(ds: DataSet, distance: Distance, window: Window, kernel: Kernel): List<List<Int>> =
        with(MutableList(ds.lines.first().y.size) { MutableList(ds.lines.first().y.size) { 0 } }) {
            ds.lines.indices.forEach {
                this[
                        NonparametricRegressor.predict(
                            DataSet(ds.header, ds.lines.toMutableList().apply { removeAt(it) }),
                            distance,
                            window,
                            kernel,
                            ds.lines.elementAt(it).x
                        )
                ][ds.lines.elementAt(it).y.indexOf(1.0)] += 1
            }
            this
        }

    fun getF1Measure(confusion: List<List<Int>>): Double {
        val all = confusion.map { it.sum() }.sum()
        val ti = confusion.mapIndexed { index, list -> list[index] }
        val pi = confusion.map { it.sum() }
        val ci = confusion.mapIndexed { index, list ->
            list.indices.map { confusion[it][index] }.sum()
        }

        val precision =
            ci.mapIndexed { index, _ -> if (pi[index] != 0) (ti[index] * ci[index]).toDouble() / pi[index] else 0.0 }.sum() / all
        val recall = ti.sum().toDouble() / all

        return 2.0 * ((precision * recall) / (precision + recall))
    }

}