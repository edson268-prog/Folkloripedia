package com.edsondev26.folkloripedia.domain.model

import com.edsondev26.folkloripedia.R

sealed class ArticleItemInfo (val img: Int, val name: Int, val origin: Int, val year: Int) {
    data object Diablada: ArticleItemInfo(R.drawable.img_article_music, R.string.music, 3, 3)
    data object Morenada: ArticleItemInfo(R.drawable.img_article_art, R.string.art, 8, 8)
    data object Tinkus: ArticleItemInfo(R.drawable.img_article_myth, R.string.myths, 5, 9)
}