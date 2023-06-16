package com.bso112.binder.example.binding

import com.bso112.binder.example.Id


abstract class IdentifiableUIModel: BindableUIModel {
    abstract val id: Id
    abstract override fun equals(other: Any?): Boolean

    override fun areItemsTheSame(other: BindableUIModel): Boolean {
        return other is IdentifiableUIModel && other::class == this::class && id == other.id
    }

    override fun areContentsTheSame(other: BindableUIModel): Boolean {
        return this == other
    }
}
