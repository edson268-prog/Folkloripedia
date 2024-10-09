package com.edsondev26.folkloripedia.domain.model

class QuizModel (
    val id: String,
    val question: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val rightAnswer: String,
    val img: String,
    val sound: String
)