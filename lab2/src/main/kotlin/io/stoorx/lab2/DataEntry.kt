package io.stoorx.lab2

data class DataEntry(
    val X: List<Double>,
    val Y: Double
) {
    val featureCount: Int
        get() = X.size
}