package com.edsondev26.folkloripedia.domain

import com.edsondev26.folkloripedia.domain.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEventByMonth(monthRequired: String): Flow<List<EventModel>>
}