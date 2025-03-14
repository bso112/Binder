package com.bso112.binder.binding


interface NoDiffUIModel : BindableUIModel {
    override fun areItemsTheSame(other: BindableUIModel): Boolean {
        return false
    }

    override fun areContentsTheSame(other: BindableUIModel): Boolean {
        return false
    }
}


