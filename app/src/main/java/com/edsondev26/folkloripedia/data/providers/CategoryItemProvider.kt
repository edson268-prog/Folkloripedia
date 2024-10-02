package com.edsondev26.folkloripedia.data.providers

import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo.*
import javax.inject.Inject

class CategoryItemProvider @Inject constructor() {
    fun getArticleItems(): List<CategoryItemInfo> {
        return listOf(
            Morenada,
            Diablada,
            Tinkus
        )
    }
}