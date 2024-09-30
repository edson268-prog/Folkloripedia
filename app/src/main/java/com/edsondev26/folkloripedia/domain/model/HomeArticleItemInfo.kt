package com.edsondev26.folkloripedia.domain.model

import com.edsondev26.folkloripedia.R

sealed class HomeArticleItemInfo (val img: Int, val name: Int, val count: Int) {
    data object Music: HomeArticleItemInfo(R.drawable.img_article_music, R.string.music, 3)
    data object Art: HomeArticleItemInfo(R.drawable.img_article_art, R.string.art, 8)
    data object Myths: HomeArticleItemInfo(R.drawable.img_article_myth, R.string.myths, 5)
}