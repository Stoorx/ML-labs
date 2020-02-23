package io.stoorx.lab1

import kotlin.math.*

interface Kernel {
    object Uniform : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else 0.5

        override fun toString(): String = "Uniform"
    }

    object Triangular : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else 1.0 - abs(u)

        override fun toString(): String = "Triangular"
    }

    object Epanechnikov : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else 0.75 * (1 - u * u)

        override fun toString(): String = "Epanechnikov"
    }

    object Quartic : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (1 - u * u).pow(2) * 15 / 16

        override fun toString(): String = "Quartic"
    }

    object Triweight : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (1 - u * u).pow(3) * 35 / 32

        override fun toString(): String = "Triweight"
    }

    object Tricube : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (1 - abs(u * u * u)).pow(3) * 70 / 81

        override fun toString(): String = "Tricube"
    }

    object Gaussian : Kernel {
        override fun invoke(u: Double) = (1 / sqrt(2 * PI)) * exp(-0.5 * u * u)

        override fun toString(): String = "Gaussian"
    }

    object Cosine : Kernel {
        override fun invoke(u: Double) = if (abs(u) >= 1.0) 0.0 else (PI / 4.0) * cos(PI * u * 0.5)

        override fun toString(): String = "Cosine"
    }

    object Logistic : Kernel {
        override fun invoke(u: Double) = 1.0 / (exp(u) + 2 + exp(-u))

        override fun toString(): String = "Logistic"
    }

    object Sigmoid : Kernel {
        override fun invoke(u: Double) = (2.0 / PI) * (1.0 / (exp(u) + exp(-u)))

        override fun toString(): String = "Sigmoid"
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
