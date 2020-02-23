package io.stoorx.lab1

interface Window {
    operator fun invoke(q: Double, sortedNeighbours: List<Pair<DataLine, Double>>): Double
}


class FixedWindow(
    private val h: Double
) : Window {
    override fun invoke(q: Double, sortedNeighbours: List<Pair<DataLine, Double>>) = q / h
}

class VariableWindow(
    private val k: Int
) : Window {
    override fun invoke(q: Double, sortedNeighbours: List<Pair<DataLine, Double>>) =
        q / sortedNeighbours[k].second
}
