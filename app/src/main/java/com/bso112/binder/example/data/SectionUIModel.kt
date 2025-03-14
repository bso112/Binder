package com.bso112.binder.example.data

import com.bso112.binder.Id
import com.bso112.binder.binding.IdentifiableUIModel
import com.bso112.binder.example.BR
import com.bso112.binder.example.R

sealed class SectionUIModel : IdentifiableUIModel {
    abstract val sectionTitle: String
    abstract val productList: List<ProductUIModel>

    var onClickSection: ((SectionUIModel) -> Unit)? = null

    data class Horizontal(
        override val id: Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_horizontal
    }

    data class Vertical(
        override val id: Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_vertical
    }

    data class Grid(
        override val id: Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_grid
    }
}

fun SectionUIModel.copy(
    id: Id = this.id,
    sectionTitle: String = this.sectionTitle,
    productList: List<ProductUIModel> = this.productList
): SectionUIModel {
    return when (this) {
        is SectionUIModel.Horizontal -> copy(id, sectionTitle, productList)
        is SectionUIModel.Vertical -> copy(id, sectionTitle, productList)
        is SectionUIModel.Grid -> copy(id, sectionTitle, productList)
    }
}