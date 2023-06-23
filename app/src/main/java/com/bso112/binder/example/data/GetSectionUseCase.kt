package com.bso112.binder.example.data

import com.bso112.binder.toId
import java.util.UUID
import javax.inject.Inject

class GetSectionUseCase @Inject constructor() : () -> List<SectionUIModel> {
    override fun invoke(): List<SectionUIModel> {
        return createFakeSectionList()
    }
}

fun createFakeSectionList(): List<SectionUIModel> {
    fun createHorizontal() =  SectionUIModel.Horizontal(
        id = UUID.randomUUID().toString().toId(),
        sectionTitle = "가로 섹션",
        productList = List(10) { ProductUIModel(product = createFakeProduct()) }
    )
    fun createGrid() = SectionUIModel.Grid(
        id = UUID.randomUUID().toString().toId(),
        sectionTitle = "그리드 섹션",
        productList = List(10) { ProductUIModel(product = createFakeProduct()) }
    )
    fun createVertical() = SectionUIModel.Vertical(
        id = UUID.randomUUID().toString().toId(),
        sectionTitle = "세로 섹션",
        productList = List(10) { ProductUIModel(product = createFakeProduct()) }
    )
    return buildList {
        repeat(10) {
            add(createHorizontal())
        }
        repeat(10) {
            add(createVertical())
        }
        repeat(10) {
            add(createGrid())
        }
        shuffle()
    }
}

fun createFakeProduct(): Product {
    return Product(
        id = UUID.randomUUID().toString(),
        name = "우유",
        image = "",
        originalPrice = 1000,
        discountedPrice = 400,
        isSoldOut = false
    )
}