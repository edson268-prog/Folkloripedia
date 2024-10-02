package com.edsondev26.folkloripedia.domain.model

import com.edsondev26.folkloripedia.R

//sealed class CategoryItemInfo (val img: Int, val name: Int, val origin: Int, val year: Int) {
//    data object Diablada: CategoryItemInfo(R.drawable.img_article_music, R.string.music, 3, 3)
//    data object Morenada: CategoryItemInfo(R.drawable.img_article_art, R.string.art, 8, 8)
//    data object Tinkus: CategoryItemInfo(R.drawable.img_article_myth, R.string.myths, 5, 9)
//}

data class CategoryItemInfo(
    val img: String,  // URL de imagen desde Firebase
    val name: String, // Nombre desde Firebase (puede ser en español o inglés)
    val region: String, // Origen
    val year: String // Año
)