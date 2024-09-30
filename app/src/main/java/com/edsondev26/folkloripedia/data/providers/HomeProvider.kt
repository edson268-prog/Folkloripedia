package com.edsondev26.folkloripedia.data.providers

import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo
import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo.*
import javax.inject.Inject

class HomeProvider @Inject constructor() {
    fun getArticles(): List<HomeArticleItemInfo> {
        return listOf(
            Music,
            Art,
            Myths
        )
    }
}