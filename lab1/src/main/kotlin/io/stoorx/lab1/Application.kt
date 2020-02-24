package io.stoorx.lab1

import java.io.File

fun main() {

    val lines = File("dataset.csv")
        .readLines()

    val header = lines.first()
        .split(',')
        .map {
            it.removeSurrounding(""""""")
        }

    val rawLines = lines.drop(1)
        .map { line ->
            line.split(',').let {
                it.dropLast(1) to it.last()
            }
        }

    val classes = rawLines.map { it.second }
        .distinct()
        .mapIndexed { index, s -> s to index }
        .toMap()

    val classCount = classes.keys.size

    val ds = DataSet(
        header.dropLast(1)
            .toMutableList()
            .plus(
                classes.toList()
                    .sortedBy { it.second }
                    .map { "class-${it.first}" }
            ),
        rawLines.map { line ->
            DataLine(
                line.first.map { it.toDouble() },
                DoubleArray(classCount) {
                    if (classes[line.second] == it)
                        1.0
                    else
                        0.0
                }.toList()
            )
        }
    ).removeFeature("region-pixel-count")
        .normalize()
        .shuffled()

    val threads = mutableListOf<Thread>()

    for (k in Kernel.getAll())
        for (d in Distance.getAll())
            threads.add(Thread {
                solve(300, d, ds, k, "${k}-${d}.csv")
            })

    threads.forEach { it.start() }
}


fun solve(kmax: Int, d: Distance, ds: DataSet, ker: Kernel, outfile: String) {
    val outFile = File(outfile).bufferedWriter()
    for (k in 1..kmax) {
        val f = LeaveOneOutValidator.getF1Measure(
            LeaveOneOutValidator.getConfusionMatrix(
                ds,
                d,
                VariableWindow(k),
                ker
            )
        )
        val str = "${d},${ker},${k},${f}"
        println(str)
        outFile.appendln(str)
        outFile.flush()
    }
}