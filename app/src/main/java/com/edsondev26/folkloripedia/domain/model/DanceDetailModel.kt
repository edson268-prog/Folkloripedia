package com.edsondev26.folkloripedia.domain.model

//@Parcelize
data class DanceDetailModel(
    val id: String,
    val name: String,
    val region: String,
    val description: String,
    val instruments: String,
    val vestment: String,
    val img: String,
    val year: String
)