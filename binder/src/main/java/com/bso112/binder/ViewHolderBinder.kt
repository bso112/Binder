package com.bso112.binder

import androidx.databinding.ViewDataBinding
import com.bso112.binder.model.BindableUIModel
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ViewHolderBinder {

    fun initialize(viewDataBinding: ViewDataBinding) {}

    fun bind(
        viewDataBinding: ViewDataBinding,
        model: BindableUIModel?,
        position: Int,
        payload: List<Any>
    )

}

interface BinderBuilder {
    fun canBind(viewDataBinding: ViewDataBinding): Boolean
    fun build(): ViewHolderBinder
}


inline fun <reified Binding : ViewDataBinding, reified Item : BindableUIModel> buildBinder(
    crossinline onInit: (Binding) -> Unit = {},
    crossinline onBind: (Binding, Item, position: Int, payload: List<Any>) -> Unit
): BinderBuilder {
    return object : BinderBuilder {
        override fun canBind(viewDataBinding: ViewDataBinding): Boolean {
            return viewDataBinding is Binding
        }

        override fun build(): ViewHolderBinder {
            return object : ViewHolderBinder {
                override fun initialize(viewDataBinding: ViewDataBinding) {
                    onInit(viewDataBinding as Binding)
                }

                override fun bind(
                    viewDataBinding: ViewDataBinding,
                    model: BindableUIModel?,
                    position: Int,
                    payload: List<Any>
                ) {
                    validate(viewDataBinding, model) { binding: Binding, item: Item ->
                        onBind(binding, item, position, payload)
                        binding.bind(item)
                    }
                }
            }
        }
    }
}

inline fun <reified Binding : ViewDataBinding, reified Item : BindableUIModel> buildBinder(
    crossinline onBind: (Binding, Item, Int) -> Unit
): BinderBuilder {
    return buildBinder<Binding, Item> { binding, item, position, _ ->
        onBind(
            binding,
            item,
            position
        )
    }
}

inline fun <reified Binding : ViewDataBinding, reified Item : BindableUIModel> buildBinder(
    crossinline onBind: (Item, Int) -> Unit
): BinderBuilder {
    return buildBinder<Binding, Item> { _, item, position -> onBind(item, position) }
}

inline fun <reified Binding : ViewDataBinding, reified Item : BindableUIModel> buildBinder(
    crossinline onBind: (Item) -> Unit
): BinderBuilder {
    return buildBinder<Binding, Item> { item, _ -> onBind(item) }
}


@OptIn(ExperimentalContracts::class)
inline fun <reified Binding, reified Item> ViewHolderBinder.validate(
    viewDataBinding: ViewDataBinding,
    item: BindableUIModel?,
    onItemNull: () -> Unit = {},
    onValid: (Binding, Item) -> Unit,
) {
    contract {
        callsInPlace(onItemNull, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onValid, InvocationKind.AT_MOST_ONCE)
    }

    if (viewDataBinding is Binding && item is Item?) {
        if (item != null) {
            onValid(viewDataBinding, item)
        } else {
            onItemNull()
        }
    } else {
        throw IllegalArgumentException("Binding or Item is not valid")
    }
}


fun <Item : BindableUIModel> ViewDataBinding.bind(item: Item) {
    setVariable(item.bindingVariableId, item)
    executePendingBindings()
}
