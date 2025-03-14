package com.bso112.binder.binding

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bso112.binder.createViewDataBinding

abstract class BindingViewHolder(val binding: ViewDataBinding) : ViewHolder(binding.root) {
    abstract fun bind(item: BindableUIModel, itemPosition: Int)
    open fun bindPayloads(item: BindableUIModel, payloads: List<Any>, itemPosition: Int) {
        bind(item, itemPosition)
    }
    open fun onAttached() {}
    open fun onDetached() {}
}

class DefaultBindingViewHolder(
    binding: ViewDataBinding
) : BindingViewHolder(binding) {
    override fun bind(item: BindableUIModel, itemPosition: Int) {
        binding.bind(item)
    }
}

fun createBindingViewHolder(
    parent: ViewGroup, @LayoutRes layoutId: Int, viewHolderBuilders: List<ViewHolderBuilder>
): BindingViewHolder {
    val binding = parent.createViewDataBinding<ViewDataBinding>(layoutId).apply {
        lifecycleOwner = parent.findViewTreeLifecycleOwner()
    }
    val viewHolderBuilder = viewHolderBuilders.find { it.canBind(binding) }
    return viewHolderBuilder?.build(binding) ?: DefaultBindingViewHolder(binding)
}

fun <Item : BindableUIModel> ViewDataBinding.bind(item: Item) {
    setVariable(item.bindingVariableId, item)
    executePendingBindings()
}
