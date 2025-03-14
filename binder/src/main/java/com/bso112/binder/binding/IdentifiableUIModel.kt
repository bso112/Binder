package com.bso112.binder.binding

import com.bso112.binder.Id

interface IdentifiableUIModel : BindableUIModel {

    val id: Id
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int

    override fun areItemsTheSame(other: BindableUIModel): Boolean {
        return other::class == this::class && other is IdentifiableUIModel && id == other.id
    }

    override fun areContentsTheSame(other: BindableUIModel): Boolean {
        return this == other
    }
}


