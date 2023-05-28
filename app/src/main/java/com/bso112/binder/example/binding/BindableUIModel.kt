package com.bso112.binder.example.binding

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil

interface BindableUIModel {
    val bindingVariableId: Int

    @get:LayoutRes
    val layoutId: Int

    fun areItemsTheSame(other: BindableUIModel): Boolean
    fun areContentsTheSame(other: BindableUIModel): Boolean

    fun getChangePayload(other: BindableUIModel): Any? {
        return null
    }
}


fun <T : BindableUIModel> createDiffer(): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areItemsTheSame(newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areContentsTheSame(newItem)
        }

        override fun getChangePayload(oldItem: T, newItem: T): Any? {
            return oldItem.getChangePayload(newItem)
        }

    }
}
