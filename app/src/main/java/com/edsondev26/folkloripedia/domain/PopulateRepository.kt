package com.edsondev26.folkloripedia.domain

import com.edsondev26.folkloripedia.domain.dto.CuriosityDto
import com.edsondev26.folkloripedia.domain.dto.DanceDto
import com.edsondev26.folkloripedia.domain.dto.EventDto
import com.edsondev26.folkloripedia.domain.dto.MythDto
import kotlinx.coroutines.flow.Flow

interface PopulateRepository {
    fun insertEvents(events: List<EventDto>): Flow<List<EventDto>>

    fun insertDances(dances: List<DanceDto>): Flow<List<DanceDto>>

    fun insertMyths(myths: List<MythDto>): Flow<List<MythDto>>

    fun insertCuriosities(curiosities: List<CuriosityDto>): Flow<List<CuriosityDto>>
}