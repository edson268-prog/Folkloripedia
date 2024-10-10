package com.edsondev26.folkloripedia.domain

import com.edsondev26.folkloripedia.domain.model.CuriosityModel
import com.edsondev26.folkloripedia.domain.model.QuizModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getCuriosityItem(curiosityId: String): Flow<CuriosityModel?>

    fun getQuizItems(questions: Number): Flow<List<QuizModel>>
}