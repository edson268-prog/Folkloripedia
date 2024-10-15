package com.edsondev26.folkloripedia.domain.model

data class EventModel (
    val id: String,
    val name: String,
    val description: String,
    val img: String,
    val month: String,
    val day:Int
)