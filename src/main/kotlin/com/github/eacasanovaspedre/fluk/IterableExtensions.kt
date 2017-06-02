package com.github.eacasanovaspedre.fluk

fun <T, R> Iterable<T>.choose(chooser: (T) -> Single<R>?) =
        asSequence().choose(chooser).asIterable()

fun <T, R> Sequence<T>.choose(chooser: (T) -> Single<R>?) =
        map(chooser).filter { it != null }.map { it!!.value }

fun <T, A, B> Sequence<T>.classify2(partitioner: (T) -> Choice2<A, B>): Pair<List<A>, List<B>> {
    val listA = mutableListOf<A>()
    val listB = mutableListOf<B>()
    map(partitioner).forEach { choice ->
        choice.execute({
            listA.add(it)
        }, {
            listB.add(it)
        })
    }
    return listA to listB
}
