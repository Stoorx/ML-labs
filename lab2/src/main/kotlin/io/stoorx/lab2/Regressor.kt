package io.stoorx.lab2

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random


object Regressor {
    data class GradientParams(
        val initialCoefficientsDispersion: Double,
        val stepSize: Double,
        val iterationsCount: Long,
        val minibatchPercent: Double
    )

    fun gradientDescent(ds: DataSet, params: GradientParams): List<Double> {
        fun gradient(w: List<Double>): List<Double> {
            val gradient = Array(w.size) { 0.0 }

            for (i in 0 until (params.minibatchPercent * ds.size / 100).toInt()) {
                val entry = ds[Random.Default.nextInt() % ds.size]
                val diff = 2.0 * (entry.X.zip(w) { a, b -> a * b }.sum() + w.last() - entry.Y)
                for (j in 0 until gradient.size - 1) {
                    gradient[j] += diff * entry.X[j]
                }
                gradient[gradient.lastIndex] += diff
            }

            return gradient.map { it / params.minibatchPercent * ds.size / 100 }
        }

        val w = ArrayList<Double>(ds.featureCount!! + 1)
        val rnd = Random.Default

        for (i in 0..ds.featureCount!!) {
            w += (2.0 * rnd.nextDouble() - 1.0) * params.initialCoefficientsDispersion
        }

        for (i in 0 until params.iterationsCount) {
            val grad = gradient(w)
            w.forEachIndexed { index, d ->
                w[index] -= grad[index] * params.stepSize
            }
        }

        return w
    }

    data class GeneticParams(
        val iterationsCount: Long,
        val initialPopulation: Long,
        val initialDispersion: Double,
        val populationSize: Long,
        val goodRate: Double,
        val mutationRate: Double
    )

    fun genetic(ds: DataSet, params: GeneticParams): List<Double> {
        fun crossover(a: List<Double>, b: List<Double>) =
            a.zip(b) { a1, b1 ->
                if (Random.Default.nextBoolean())
                    if (Random.Default.nextBoolean()) a1 else b1
                else a1 + b1 / 2.0
            }

        fun mutate(o: List<Double>) =
            o.map {
                if (Random.Default.nextDouble() < params.mutationRate)
                    it * (2.0 * Random.Default.nextDouble() - 1.0) * params.initialDispersion
                else it
            }

        fun rmse(w: List<Double>) =
            sqrt(ds.entries.map {
                (it.X.zip(w) { a, b -> a * b }.sum() + w.last() - it.Y).pow(2)
            }.sum()) / ds.size.toDouble().pow(2)

        var population: MutableList<Pair<List<Double>, Double>> = ArrayList<Pair<List<Double>, Double>>()
        for (i in 0 until params.initialPopulation) {
            population.add(
                Array(ds.featureCount!! + 1) {
                    (2.0 * Random.Default.nextDouble() - 1.0) * params.initialDispersion
                }.toList().let {
                    it to rmse(it)
                }
            )
        }

        for (i in 0 until params.iterationsCount) {
            population.sortBy { it.second }
            population = population.dropLast((1.0 - population.size * params.goodRate).toInt()).toMutableList()

            val newEntries = population.map {
                crossover(it.first, population[Random.Default.nextInt() % population.size].first)
                    .let { it to rmse(it) }
            }

            population.addAll(newEntries)
        }
        return population.minBy { it.second }!!.first
    }
}