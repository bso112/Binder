package com.bso112.binder.example.data

import com.bso112.binder.example.BR
import com.bso112.binder.example.R
import com.bso112.binder.model.IdentifiableUIModel

sealed class SectionUIModel : IdentifiableUIModel() {
    abstract val sectionTitle: String
    abstract val productList: List<ProductUIModel>

    var onClickSection: ((SectionUIModel) -> Unit)? = null

    data class Horizontal(
        override val id: com.bso112.binder.Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_horizontal
    }

    data class Vertical(
        override val id: com.bso112.binder.Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_vertical
    }

    data class Grid(
        override val id: com.bso112.binder.Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_grid
    }
}