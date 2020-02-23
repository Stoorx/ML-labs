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

    val outFile = File("out.csv").bufferedWriter()

    for (dist in Distance.getAll()) {
        for (k in 1..30) {
            for (kern in Kernel.getAll()) {
                val f = LeaveOneOutValidator.getF1Measure(
                    LeaveOneOutValidator.getConfusionMatrix(
                        ds,
                        dist,
                        VariableWindow(k),
                        kern
                    )
                )
                val str = "${dist},${k},${kern},${f}"
                println(str)
                outFile.appendln(str)
            }
        }
    }
}