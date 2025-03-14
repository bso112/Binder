package com.bso112.binder.binding

import androidx.databinding.ViewDataBinding


interface ViewHolderBuilder {
    fun canBind(binding: ViewDataBinding): Boolean
    fun build(binding: ViewDataBinding): BindingViewHolder
}

inline fun <reified BINDING : ViewDataBinding, reified ITEM : BindableUIModel> viewHolderBuilder(
    crossinline onInit: ((BINDING) -> Unit) = {},
    crossinline onBind: (BINDING, ITEM, Int) -> Unit = { _, _, _ -> }
): ViewHolderBuilder {
    return object : ViewHolderBuilder {
        override fun canBind(binding: ViewDataBinding): Boolean {
            return binding is BINDING
        }

        override fun build(binding: ViewDataBinding): BindingViewHolder {
            return object : BindingViewHolder(binding) {

                init {
                    onInit(binding as BINDING)
                }

                override fun bind(item: BindableUIModel, itemPosition: Int) {
                    if (binding is BINDING && item is ITEM) {
                        onBind(binding, item, itemPosition)
                        binding.bind(item)
                    } else {
                        error("can not bind ${ITEM::class} with ${BINDING::class}")
                    }
                }
            }
        }
    }
}

inline fun <reified BINDING : ViewDataBinding, reified ITEM : BindableUIModel> viewHolderBuilder(
    crossinline onInit: ((BINDING) -> Unit) = {},
    crossinline onBind: (ITEM) -> Unit
): ViewHolderBuilder {
    return viewHolderBuilder<BINDING, ITEM>(onInit) { _, item, _ -> onBind(item) }
}


inline fun <reified BINDING : ViewDataBinding, reified ITEM : BindableUIModel> viewHolderBuilder(
    crossinline onInit: ((BINDING) -> Unit) = {},
    crossinline onBind: (ITEM, Int) -> Unit
): ViewHolderBuilder {
    return viewHolderBuilder<BINDING, ITEM>(onInit) { _, item, position -> onBind(item, position) }
}

inline fun <reified BINDING : ViewDataBinding> customViewHolderBuilder(crossinline builder: (BINDING) -> BindingViewHolder): ViewHolderBuilder {
    return object : ViewHolderBuilder {
        override fun canBind(binding: ViewDataBinding): Boolean {
            return binding is BINDING
        }

        override fun build(binding: ViewDataBinding): BindingViewHolder {
            return if (binding is BINDING) {
                builder(binding)
            } else {
                error("can not bind with ${BINDING::class}")
            }
        }
    }
}
