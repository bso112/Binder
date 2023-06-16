package com.bso112.binder.example.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class SectionPagingSource(
    private val getSectionUseCase: GetSectionUseCase
) : PagingSource<Int, SectionUIModel>() {
    override fun getRefreshKey(state: PagingState<Int, SectionUIModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SectionUIModel> {
        return try {
            val currentPage = params.key ?: 1
            val sections = getSectionUseCase()
            LoadResult.Page(
                data = sections,
                prevKey = null,
                nextKey =  null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}