package com.bso112.binder.example.util

import androidx.paging.PagingDataAdapter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

interface PagingStateHolder {
    val isLoading: StateFlow<Boolean>
    val isEmpty : StateFlow<Boolean>
    val pagingError: SharedFlow<Throwable>
    suspend fun connectPagingState(adapter: PagingDataAdapter<*, *>)
}

class PagingStateHolderImpl : PagingStateHolder {
    private val _isLoading = MutableStateFlow(false)
    override val isLoading = _isLoading.asStateFlow()

    private val _isEmpty = MutableStateFlow(false)
    override val isEmpty: StateFlow<Boolean> = _isEmpty.asStateFlow()

    private val _error = MutableSharedFlow<Throwable>()
    override val pagingError = _error.asSharedFlow()

    override suspend fun connectPagingState(adapter: PagingDataAdapter<*, *>) {
        adapter.loadStateFlow.collect {
            it.getError()?.alsoSuspend(_error::emit)
            _isLoading.emit(it.isLoading())
            _isEmpty.emit(it.isEmpty(adapter.itemCount))
        }
    }
}
