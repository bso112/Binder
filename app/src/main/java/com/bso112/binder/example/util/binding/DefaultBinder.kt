package com.bso112.binder.example.util.binding

import androidx.databinding.ViewDataBinding

class DefaultBinder : ViewHolderBinder {
    override fun bind(
        viewDataBinding: ViewDataBinding,
        model: BindableUIModel?,
        position: Int,
        payload: List<Any>
    ) {
        model?.also(viewDataBinding::bind)
    }
}

