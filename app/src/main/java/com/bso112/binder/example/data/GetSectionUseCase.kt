package com.bso112.binder.example.data

import com.bso112.binder.example.toId
import java.util.UUID
import javax.inject.Inject

class GetSectionUseCase @Inject constructor() : () -> List<SectionUIModel> {
    override fun invoke(): List<SectionUIModel> {
        return createFakeSectionList()
    }
}

fun createFakeSectionList(): List<SectionUIModel> {
    val horizontal = SectionUIModel.Horizontal(
        id = UUID.randomUUID().toString().toId(),
        sectionTitle = "가로 섹션",
        productList = List(10) { ProductUIModel(product = createFakeProduct()) }
    )
    val grid = SectionUIModel.Grid(
        id = UUID.randomUUID().toString().toId(),
        sectionTitle = "그리드 섹션",
        productList = List(10) { ProductUIModel(product = createFakeProduct()) }
    )
    val vertical = SectionUIModel.Vertical(
        id = UUID.randomUUID().toString().toId(),
        sectionTitle = "세로 섹션",
        productList = List(10) { ProductUIModel(product = createFakeProduct()) }
    )
    return buildList {
        repeat(10) {
            add(horizontal.copy())
        }
        repeat(10) {
            add(grid.copy())
        }
        repeat(10) {
            add(vertical.copy())
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