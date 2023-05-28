package com.bso112.binder.example.data

import com.bso112.binder.example.data.Section
import javax.inject.Inject

class GetSectionUseCase @Inject constructor() : () -> List<Section> {
    override fun invoke(): List<Section> {
        return listOf(
            Section.Horizontal(id = 1, title = "Section Horizontal"),
            Section.Vertical(id = 2, title = "Section Vertical"),
            Section.Grid(id = 3, title = "Section Grid")
        )
    }
}