package com.bso112.binder.example.util.binding

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class BindingListAdapter(
    private vararg val binderBuilders: BinderBuilder
) : ListAdapter<BindableUIModel, BindingViewHolder>(createDiffer()) {

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

