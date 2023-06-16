package com.bso112.binder.example.util.paging

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.flatMap

fun CombinedLoadStates.isEmpty(count: Int) =
    source.refresh is LoadState.NotLoading && source.prepend.endOfPaginationReached && count < 1

fun CombinedLoadStates.hasError(): Boolean = allState.any { it is LoadState.Error }

fun CombinedLoadStates.getError(): Throwable? =
    allState.firstNotNullOfOrNull { it as? LoadState.Error }?.error

fun CombinedLoadStates.isLoading(): Boolean = allState.any { it is LoadState.Loading }


val CombinedLoadStates.allState: List<LoadState>
    get() = listOf(
        refresh,
        prepend,
        append
    )


fun <T : Any, R : Any> PagingData<T>.flatMapIndexed(block: (index: Int, item: T) -> List<R>): PagingData<R> {
    var index = 0
    return flatMap {
        block(index, it).also {
            index += 1
        }
    }
}

