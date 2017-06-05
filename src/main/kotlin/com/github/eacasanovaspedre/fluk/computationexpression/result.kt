package com.github.eacasanovaspedre.fluk.computationexpression

import com.github.eacasanovaspedre.fluk.*
import kotlin.coroutines.experimental.*

@RestrictsSuspension
class Computation<G, B> : Continuation<G> {

    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resume(value: G) {
        this.result = value.asSuccess()
    }

    override fun resumeWithException(exception: Throwable) {
        throw exception
    }

    lateinit var result: Result<G, B>

    suspend fun <T> Result<T, B>.pull(): T = suspendCoroutine { continuation ->
        when (this) {
            is Success -> continuation.resume(value)
            is Failure -> result = this@pull.value.asFailure<G, B>()
        }
    }
}

fun <G, B> result(flow: suspend Computation<G, B>.() -> G): Result<G, B> = with(Computation<G, B>()) {
    flow.startCoroutine(this, this)
    result
}