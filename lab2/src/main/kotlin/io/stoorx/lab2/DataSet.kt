package io.stoorx.lab2

data class DataSet(
    val entries: List<DataEntry>
) {
    val size: Int
        get() = entries.size

    val featureCount: Int?
        get() = entries.firstOrNull()?.featureCount

    operator fun get(index: Int) = entries[index]
}