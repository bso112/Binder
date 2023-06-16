package com.bso112.binder.example.util.binding

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bso112.binder.example.util.createViewDataBinding

class BindingViewHolder(
    private val binding: ViewDataBinding,
    private val binder: ViewHolderBinder
) : ViewHolder(binding.root) {

    init {
        binder.initialize(binding)
    }

    fun bind(item: BindableUIModel, itemPosition: Int, payload : List<Any> = emptyList()) {
        binder.bind(binding, item, itemPosition, payload)
    }

}

fun createBindingViewHolder(
    parent: ViewGroup, @LayoutRes layoutId: Int, binderBuilderList: List<BinderBuilder>
): BindingViewHolder {
    val binding = parent.createViewDataBinding<ViewDataBinding>(layoutId).apply {
        lifecycleOwner = parent.findViewTreeLifecycleOwner()
    }
    val binderBuilder = binderBuilderList.find { it.canBind(binding) }
    return BindingViewHolder(binding, binderBuilder?.build() ?: DefaultBinder())
}

