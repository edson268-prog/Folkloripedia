package com.edsondev26.folkloripedia.domain

import com.edsondev26.folkloripedia.domain.model.CuriosityModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getCuriosityItem(curiosityId: String): Flow<CuriosityModel?>
}