package com.bso112.binder.example.util.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class PagingDataModifier<T : Any>(
    externalScope: CoroutineScope,
    pagingDataFactory: () -> Flow<PagingData<T>>
) {
    private val modifications = MutableStateFlow<MutableList<Modification<T>>>(mutableListOf())

    val stateFlow = pagingDataFactory().applyModifications(modifications)
        .stateIn(externalScope, SharingStarted.WhileSubscribed(5000L), PagingData.empty())

    fun modify(modification: Modification<T>) {
        modifications.update { (it + modification).toList().toMutableList() }
    }

    fun cache(list: List<T>) {
        modifications.value.clear()
        modifications.value.add(Modification.Cache(list))
    }
}

fun <T : Any> ViewModel.pagingDataModifier(pagingDataFactory: () -> Flow<PagingData<T>>) =
    PagingDataModifier(viewModelScope, pagingDataFactory)


fun <T : Any> Flow<PagingData<T>>.applyModifications(modifications: StateFlow<List<Modification<T>>>): Flow<PagingData<T>> {
    return combine(modifications) { pagingData, modification ->
        modification.fold(pagingData) { acc, mod ->
            acc.applyModifications(mod)
        }
    }
}

fun <T : Any> PagingData<T>.applyModifications(mod: Modification<T>): PagingData<T> {
    return mod.invoke(this)
}
