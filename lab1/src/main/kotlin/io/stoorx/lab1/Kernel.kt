package io.stoorx.lab1

import kotlin.math.*

interface Kernel {
    object Uniform : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else 0.5
    }

    object Triangular : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else 1.0 - abs(u)
    }

    object Epanechnikov : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else 0.75 * (1 - u * u)
    }

    object Quartic : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (1 - u * u).pow(2) * 15 / 16
    }

    object Triweight : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (1 - u * u).pow(3) * 35 / 32
    }

    object Tricube : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (1 - abs(u * u * u)).pow(3) * 70 / 81
    }

    object Gaussian : Kernel {
        override fun invoke(u: Double) = (1 / sqrt(2 * PI)) * exp(-0.5 * u * u)
    }

    object Cosine : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (PI / 4.0) * cos(PI * u * 0.5)
    }

    object Logistic : Kernel {
        override fun invoke(u: Double) = 1.0 / (exp(u) + 2 + exp(-u))
    }

    object Sigmoid : Kernel {
        override fun invoke(u: Double) = (2.0 / PI) * (1.0 / (exp(u) + exp(-u)))
    }

    operator fun invoke(u: Double): Double

    companion object {
        fun getAll(): List<Kernel> = listOf(
            Uniform,
            Triangular,
            Epanechnikov,
            Quartic,
            Triweight,
            Tricube,
            Gaussian,
            Cosine,
            Logistic,
            Sigmoid
        )
    }
}
