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
    )

    println(ds)
}