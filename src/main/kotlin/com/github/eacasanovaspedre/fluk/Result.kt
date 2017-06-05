package com.github.eacasanovaspedre.fluk


sealed class Result<out TSuccess, out TFailure>

data class Success<out TSuccess, out TFailure>(val value: TSuccess) : Result<TSuccess, TFailure>()

data class Failure<out TSuccess, out TFailure>(val value: TFailure) : Result<TSuccess, TFailure>()

fun <TSuccess, TFailure> TSuccess.asSuccess() = Success<TSuccess, TFailure>(this)

fun <TSuccess, TFailure> TFailure.asFailure() = Failure<TSuccess, TFailure>(this)

val <TSuccess, TFailure> Result<TSuccess, TFailure>.isSuccess get() = this is Success

val <TSuccess, TFailure> Result<TSuccess, TFailure>.isFailure get() = this is Failure

inline fun <TNewSuccess, TNewFailure, TSuccess, TFailure>
        Result<TSuccess, TFailure>.map(mapperOnSuccess: (TSuccess) -> TNewSuccess,
                                       mapperOnFailure: (TFailure) -> TNewFailure): Result<TNewSuccess, TNewFailure> =
        when (this) {
            is Success -> Success(mapperOnSuccess(this.value))
            is Failure -> Failure(mapperOnFailure(this.value))
        }

inline infix fun <TNewSuccess, TSuccess, TFailure>
        Result<TSuccess, TFailure>.map(mapper: (TSuccess) -> TNewSuccess): Result<TNewSuccess, TFailure> =
        this.map(mapper, ::id)

inline fun <TNewFailure, TSuccess, TFailure> Result<TSuccess, TFailure>.mapFailure(
        mapper: (TFailure) -> TNewFailure): Result<TSuccess, TNewFailure> {
    return this.map(::id, mapper)
}

inline infix fun <TNewSuccess, TSuccess, TFailure>
        Result<TSuccess, TFailure>.bind(binder: (TSuccess) -> Result<TNewSuccess, TFailure>)
        : Result<TNewSuccess, TFailure> = when (this) {
    is Success -> binder(this.value)
    is Failure -> Failure(this.value)
}

inline infix fun <TSuccess, TFailure>
        Result<TSuccess, TFailure>.tryToRecover(func: (TFailure) -> Single<TSuccess>?): Result<TSuccess, TFailure> =
        when (this) {
            is Success -> this
            is Failure -> func(value)?.value?.asSuccess() ?: this
        }

inline fun <TReturn, TSuccess, TFailure>
        Result<TSuccess, TFailure>.fold(onSuccess: (TSuccess) -> TReturn, onFailure: (TFailure) -> TReturn): TReturn =
        when (this) {
            is Success -> onSuccess(this.value)
            is Failure -> onFailure(this.value)
        }

inline fun <TSuccess> Result<TSuccess, *>.orElse(generator: () -> TSuccess): TSuccess = orElse(generator())

fun <TSuccess> Result<TSuccess, *>.orElse(value: TSuccess): TSuccess = fold(::id, { value })

inline fun <A, B, TSuccessA, TSuccessB, TFailure>
        Pair<A, B>.combineResults(expanderA: (A) -> Result<TSuccessA, TFailure>,
                                  expanderB: (B) -> Result<TSuccessB, TFailure>) =
        expanderA(first) bind { firstSuccess ->
            expanderB(second).map { secondSuccess ->
                firstSuccess to secondSuccess
            }
        }

inline fun <A, TSuccess, TFailure> Pair<A, A>.combineResults(expander: (A) -> Result<TSuccess, TFailure>) =
        combineResults(expander, expander)

inline fun <T, TSuccess, TFailure>
        Iterable<T>.combineResults(expander: (T) -> Result<TSuccess, TFailure>): Result<List<TSuccess>, TFailure> {
    val list = mutableListOf<TSuccess>()
    for (result in map(expander)) {
        when (result) {
            is Success -> list.add(result.value)
            is Failure -> return result.value.asFailure()
        }
    }
    return list.asSuccess()
}

inline fun <T, TSuccess, TFailure>
        Sequence<T>.combineResults(crossinline expander: (T) -> Result<TSuccess, TFailure>)
        : Result<List<TSuccess>, TFailure> {
    val list = mutableListOf<TSuccess>()
    for (result in map { expander(it) }) {
        when (result) {
            is Success -> list.add(result.value)
            is Failure -> return result.value.asFailure()
        }
    }
    return list.asSuccess()
}

fun <TSuccess, TFailure> Sequence<Result<TSuccess, TFailure>>.classify2(): Pair<List<TSuccess>, List<TFailure>> =
        classify2 {
            when (it) {
                is Success -> it.value.asChoice1Of2<TSuccess, TFailure>()
                is Failure -> it.value.asChoice2Of2<TSuccess, TFailure>()
            }
        }