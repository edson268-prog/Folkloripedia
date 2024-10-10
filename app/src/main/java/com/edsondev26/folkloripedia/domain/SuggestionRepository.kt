package com.edsondev26.folkloripedia.domain

import com.edsondev26.folkloripedia.domain.model.SuggestionModel
import kotlinx.coroutines.flow.Flow

interface SuggestionRepository {
    fun setSuggestion(suggestion: SuggestionModel): Flow<String>

    fun isAllowedToSend(): Flow<Boolean>
}