package com.edsondev26.folkloripedia.data.providers

import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo.*
import javax.inject.Inject

class ArticleItemProvider @Inject constructor() {
    fun getArticleItems(): List<ArticleItemInfo> {
        return listOf(
            Morenada,
            Diablada,
            Tinkus
        )
    }
}