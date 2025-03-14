package com.bso112.binder.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bso112.binder.Id
import com.bso112.binder.example.data.GetSectionUseCase
import com.bso112.binder.example.data.SectionPagingSource
import com.bso112.binder.example.data.SectionUIModel
import com.bso112.binder.example.data.copy
import com.bso112.binder.example.util.paging.Modification
import com.bso112.binder.example.util.paging.PagingStateHolder
import com.bso112.binder.example.util.paging.PagingStateHolderImpl
import com.bso112.binder.example.util.paging.pagingDataModifier

class MainViewModel(
    private val getSectionUseCase: GetSectionUseCase
) : ViewModel(),
    PagingStateHolder by PagingStateHolderImpl() {

    private val _sectionList = pagingDataModifier {
        Pager(
            config = PagingConfig(pageSize = 5, initialLoadSize = 15)
        ) {
            SectionPagingSource(getSectionUseCase)
        }.flow.cachedIn(viewModelScope)
    }

    val sectionList = _sectionList.stateFlow


    fun provideSnapShop(snapShop: List<SectionUIModel>) {
        _sectionList.cache(snapShop)
    }

    fun deleteSection(section: SectionUIModel) {
        _sectionList.modify(Modification.Delete(section))
    }

    fun toggleProductLike(productId: Id) {
        _sectionList.modify(Modification.Update(
            selector = {
                it.productList.any { it.id == productId }
            },
            updater = { section ->
                val newList = section.productList.map { item ->
                    if (item.id == productId) {
                        item.copy(isLike = !item.isLike)
                    } else {
                        item
                    }
                }
                section.copy(productList = newList)
            }
        ))
    }


}