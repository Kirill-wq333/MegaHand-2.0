package com.evothings.domain.feature.catalog.model.statical

import com.evothings.domain.feature.catalog.model.FilterValue

val filterKeys = listOf("Сортировка", "Цвет", "Материал", "Размер", "Качество")

val sortingFilterValues = listOf(
    FilterValue(-2, "Сначала дешевые"),
    FilterValue(-1, "Сначала дорогие")
)
