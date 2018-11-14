package rationals

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Rational(argn: Number, argd: Number = 1): Comparable<Rational> {
    private val ggt = ggt(argn.bd(), argd.bd()) * (if (argd.bd() < ZERO) -ONE else ONE)
    val n: BigInteger = argn.bd() / ggt
    val d: BigInteger = argd.bd() / ggt
    init {
        if (d == ZERO) throw IllegalArgumentException()
    }

    tailrec fun ggt(a: BigInteger, b: BigInteger): BigInteger {
        if (a < ZERO || b < ZERO) return ggt(a.abs(), b.abs())
        return if (b == ZERO) a else ggt(b, a % b)
    }

    private fun Number.bd(): BigInteger {
        return when (this) {
            is BigInteger -> this
            is Long -> toBigInteger()
            is Int -> toBigInteger()
            else -> throw IllegalArgumentException()
        }
    }

    operator fun plus(r: Rational) = Rational(n * r.d + r.n * d, d * r.d)
    operator fun minus(r: Rational) = Rational(n * r.d - r.n * d, d * r.d)
    operator fun unaryMinus() = Rational(-n, d)
    operator fun times(r: Rational) = Rational(n * r.n, d * r.d)
    operator fun div(r: Rational) = Rational(n * r.d, d * r.n)

    override fun equals(r: Any?): Boolean {
        return when(r) {
            null -> false
            is Rational -> n * r.d == r.n * d
            else -> false
        }
    }
    override fun compareTo(r: Rational): Int {
        val temp = n * r.d - r.n * d
        return when {
            temp < ZERO -> -1
            temp == ZERO -> 0
            else -> 1
        }
    }

    override fun toString(): String {
        return if (d == ONE) "$n" else "$n/$d"
    }
}

infix fun Number.divBy(i: Number) = Rational(this, i)


fun String.toRational(): Rational {
    val parts = this.split("/")
    return when (parts.size) {
        1 -> Rational(parts[0].toBigInteger())
        2 -> Rational(parts[0].toBigInteger(), parts[1].toBigInteger())
        else -> throw java.lang.IllegalArgumentException()
    }
}

fun main(args: Array<String>) {
    val r1 = 1 divBy 2
    val r2 = 2000000000L divBy 4000000000L
    println(r1 == r2)

    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    println("1/2".toRational() - "1/3".toRational() == "1/6".toRational())
    println("1/2".toRational() + "1/3".toRational() == "5/6".toRational())

    println(-(1 divBy 2) == (-1 divBy 2))

    println((1 divBy 2) * (1 divBy 3) == "1/6".toRational())
    println((1 divBy 2) / (1 divBy 4) == "2".toRational())

    println((1 divBy 2) < (2 divBy 3))
    println((1 divBy 2) in (1 divBy 3)..(2 divBy 3))

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}




