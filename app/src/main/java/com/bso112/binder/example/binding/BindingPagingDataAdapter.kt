package com.bso112.binder.example.binding

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

class BindingPagingDataAdapter<T : BindableUIModel>(
    private vararg val binderBuilders: BinderBuilder
) : PagingDataAdapter<T, BindingViewHolder>(createDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return createBindingViewHolder(parent, viewType, binderBuilders.toList())
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            getItem(position)?.let {
                holder.bind(it, position, payloads)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.layoutId ?: error("cannot find item at $position")
    }

}
