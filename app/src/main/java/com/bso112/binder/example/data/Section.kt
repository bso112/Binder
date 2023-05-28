package com.bso112.binder.example.data

import com.bso112.binder.example.BR
import com.bso112.binder.example.R
import com.bso112.binder.example.binding.BindableUIModel

sealed class Section : BindableUIModel {
    abstract val id: Int
    abstract val title: String

    class Horizontal(
        override val id: Int,
        override val title: String
    ) : Section() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_horizontal
        override fun areItemsTheSame(other: BindableUIModel): Boolean {
            return other is Horizontal && id == other.id
        }

        override fun areContentsTheSame(other: BindableUIModel): Boolean {
            return this == other
        }
    }

    class Vertical(
        override val id: Int,
        override val title: String
    ) : Section() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_vertical
        override fun areItemsTheSame(other: BindableUIModel): Boolean {
            return other is Horizontal && id == other.id
        }

        override fun areContentsTheSame(other: BindableUIModel): Boolean {
            return this == other
        }
    }

    class Grid(
        override val id: Int,
        override val title: String
    ) : Section() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_grid
        override fun areItemsTheSame(other: BindableUIModel): Boolean {
            return other is Horizontal && id == other.id
        }

        override fun areContentsTheSame(other: BindableUIModel): Boolean {
            return this == other
        }
    }


}