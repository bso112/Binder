package com.bso112.binder.example.util


suspend fun <T> T.alsoSuspend(block: suspend (T) -> Unit) =
    also {
        block(it)
    }


suspend fun <T, R> T.letSuspend(block: suspend (T) -> R) =
    let {
        block(it)
    }


fun <T> Iterable<T>.forEachApply(block: T.() -> Unit): List<T> {
    forEach {
        it.apply(block)
    }
    return toList()
}


fun <T> List<T>.add(item: T, index: Int = size): List<T> {
    return toMutableList().apply { add(index, item) }
}

fun <T> List<T>.replace(oldItem: T, newItem: T): List<T> {
    var isReplaced = false
    val newList = map {
        if (it == oldItem) {
            isReplaced = true
            newItem
        } else {
            it
        }
    }
    return if (isReplaced) newList else this
}

fun <T> List<T>.update(selector: (T) -> Boolean, newItemFactory: (T) -> T): List<T> {
    var isUpdated = false
    val newList = map {
        if (selector(it)) {
            isUpdated = true
            newItemFactory(it)
        } else {
            it
        }
    }
    return if (isUpdated) newList else this
}
