package com.bso112.binder.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bso112.binder.example.data.GetSectionUseCase
import com.bso112.binder.example.data.SectionPagingSource
import com.bso112.binder.example.data.SectionUIModel
import com.bso112.binder.example.util.paging.PagingStateHolder
import com.bso112.binder.example.util.paging.PagingStateHolderImpl
import com.bso112.binder.example.util.paging.pagingDataModifier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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


}