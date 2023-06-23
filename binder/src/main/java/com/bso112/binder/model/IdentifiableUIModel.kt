package com.bso112.binder.model

import com.bso112.binder.Id


abstract class IdentifiableUIModel : BindableUIModel {
    abstract val id: Id
    abstract override fun equals(other: Any?): Boolean

    override fun areItemsTheSame(other: BindableUIModel): Boolean {
        return other::class == this::class && other is IdentifiableUIModel && id == other.id
    }

    override fun areContentsTheSame(other: BindableUIModel): Boolean {
        return this == other
    }
}
