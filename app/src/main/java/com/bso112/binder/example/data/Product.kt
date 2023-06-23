package com.bso112.binder.example.data

data class Product(
    val id: String,
    val name: String,
    val image: String,
    val originalPrice: Int,
    val discountedPrice: Int?,
    val isSoldOut: Boolean
) {
    val finalPrice: Int get() = discountedPrice ?: originalPrice
}
