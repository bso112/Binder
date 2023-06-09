package com.bso112.binder.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bso112.binder.BinderBuilder
import com.bso112.binder.BindingViewHolder
import com.bso112.binder.createBindingViewHolder
import com.bso112.binder.model.BindableUIModel
import com.bso112.binder.model.createDiffer

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

