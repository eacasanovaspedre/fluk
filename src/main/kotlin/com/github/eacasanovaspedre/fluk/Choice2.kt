package com.github.eacasanovaspedre.fluk

sealed class Choice2<out A, out B>

data class Choice1Of2<out A, out B>(val value: A): Choice2<A, B>()

data class Choice2Of2<out A, out B>(val value: B): Choice2<A, B>()

fun <A, B> A.asChoice1Of2(): Choice2<A, B> = Choice1Of2(this)

fun <A, B> B.asChoice2Of2(): Choice2<A, B> = Choice2Of2(this)

inline fun <A, B> Choice2<A, B>.execute(onA: (A) -> Unit, onB: (B) -> Unit) = when(this) {
    is Choice1Of2 -> onA(value)
    is Choice2Of2 -> onB(value)
}
