package com.bso112.binder

import androidx.databinding.ViewDataBinding
import com.bso112.binder.model.BindableUIModel

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

