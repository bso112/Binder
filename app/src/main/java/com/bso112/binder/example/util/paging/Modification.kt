package com.bso112.binder.example.util.paging

import androidx.paging.PagingData
import androidx.paging.map


interface Modification<T : Any> : (PagingData<T>) -> PagingData<T> {
    class Cache<T : Any>(val list: List<T>) : Modification<T> {
        override fun invoke(p1: PagingData<T>): PagingData<T> {
            return PagingData.from(list)
        }
    }

    class Add<T : Any>(
        private val position: Int,
        private val newItem: T
    ) : Modification<T> {
        override fun invoke(p1: PagingData<T>): PagingData<T> {
            return p1.flatMapIndexed { index, item ->
                if (position == index) {
                    listOf(newItem, item)
                } else {
                    listOf(item)
                }
            }
        }
    }

    class Update<T : Any>(
        private val selector : (T)->Boolean,
        private val updater : (T)->T
    ) : Modification<T> {
        override fun invoke(p1: PagingData<T>): PagingData<T> {
            return p1.map {
                if(selector(it)){
                    updater(it)
                } else {
                    it
                }
            }
        }
    }

}
