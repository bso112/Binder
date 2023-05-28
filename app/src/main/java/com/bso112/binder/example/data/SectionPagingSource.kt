package com.bso112.binder.example.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class SectionPagingSource(
    private val getSectionUseCase: GetSectionUseCase
) : PagingSource<Int, Section>() {
    override fun getRefreshKey(state: PagingState<Int, Section>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Section> {
        return try {
            val currentPage = params.key ?: 1
            val sections = getSectionUseCase()
            LoadResult.Page(
                data = sections,
                prevKey = null,
                nextKey =  currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}