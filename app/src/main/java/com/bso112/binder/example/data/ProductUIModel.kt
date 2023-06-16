package com.bso112.binder.example.data

import com.bso112.binder.example.Id
import com.bso112.binder.example.util.binding.IdentifiableUIModel
import com.bso112.binder.example.toId
import com.bso112.binder.example.BR
import com.bso112.binder.example.R
import kotlin.math.roundToInt

data class ProductUIModel(
    val product: Product,
    val isLike: Boolean = false,
) : IdentifiableUIModel() {
    override val id: Id = product.id.toId()
    override val bindingVariableId: Int = BR.item

    var onClick: (() -> Unit)? = null
    var onClickFavorite: ((ProductUIModel) -> Unit)? = null

    override val layoutId: Int = R.layout.item_product_normal

    fun calculateDiscountRate(): String = runCatching {
        requireNotNull(product.discountedPrice)
        require(product.originalPrice > 0)
        require(product.discountedPrice > 0)
        require(product.originalPrice >= product.discountedPrice)
        val delta = product.originalPrice - product.discountedPrice
        (delta / product.originalPrice.toFloat() * 100).roundToInt()
    }.getOrDefault(0).toString() + "%"

    val finalPrice = "${product.finalPrice}원"
    val originalPrice = "${product.originalPrice}원"
}
