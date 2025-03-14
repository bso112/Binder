package com.bso112.binder.binding

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class BindingListAdapter(
    private vararg val viewHolderBuilders: ViewHolderBuilder
) : ListAdapter<BindableUIModel, BindingViewHolder>(createDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return createBindingViewHolder(parent, viewType, viewHolderBuilders.toList())
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int, payloads: List<Any>) {
        getItem(position)?.let {
            if (payloads.isNotEmpty()) {
                holder.bindPayloads(it, payloads, position)
            } else {
                onBindViewHolder(holder, position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.layoutId ?: error("cannot find item at $position")
    }

    override fun onViewAttachedToWindow(holder: BindingViewHolder) {
        holder.onAttached()
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder) {
        holder.onDetached()
    }
}

