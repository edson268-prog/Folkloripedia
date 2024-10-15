package com.edsondev26.folkloripedia.data

import android.content.Context
import android.util.Log
import com.edsondev26.folkloripedia.domain.PopulateRepository
import com.edsondev26.folkloripedia.domain.dto.DanceDto
import com.edsondev26.folkloripedia.domain.dto.EventDto
import com.edsondev26.folkloripedia.domain.dto.MythDto
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PopulateRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : PopulateRepository {

    override fun insertEvents(events: List<EventDto>): Flow<List<EventDto>> = flow {
        try {
            val batch = firestore.batch()

            events.forEach { event ->
                val docName = "bolivia_evento_${event.id}"
                Log.d("Populate", "Evento docName: $docName")
                val eventRef = firestore.collection("Events").document(docName)
                val eventData = hashMapOf(
                    "Day" to event.day,
                    "Description" to event.description,
                    "Description_en" to event.description_en,
                    "Image" to event.img,
                    "Month" to event.month,
                    "Name" to event.name,
                    "Name_en" to event.name_en,
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(events)
        } catch (e: Exception) {
            emit(emptyList<EventDto>())
            e.printStackTrace()
        }
    }

    override fun insertDances(dances: List<DanceDto>): Flow<List<DanceDto>> = flow {
        try {
            val batch = firestore.batch()

            dances.forEach { dance ->
                val docName = "bolivia_danza_${dance.id}"
                val eventRef = firestore.collection("Dances").document(docName)
                val eventData = hashMapOf(
                    "Description" to dance.description,
                    "Description_en" to dance.description_en,
                    "Image" to dance.img,
                    "Instruments" to dance.instruments,
                    "Instruments_en" to dance.instruments_en,
                    "Name" to dance.name,
                    "Region" to dance.region,
                    "Type" to dance.type,
                    "Vestment" to dance.vestment,
                    "Year_Origin" to dance.year,
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(dances)
        } catch (e: Exception) {
            emit(emptyList<DanceDto>())
            e.printStackTrace()
        }
    }

    override fun insertMyths(myths: List<MythDto>): Flow<List<MythDto>> = flow {
        try {
            val batch = firestore.batch()

            myths.forEach { myth ->
                val docName = "bolivia_mito_${myth.id}"
                val eventRef = firestore.collection("Myths").document(docName)
                val eventData = hashMapOf(
                    "Name" to myth.name,
                    "Origin" to myth.origin,
                    "Image" to myth.img,
                    "Tale" to myth.tale,
                    "Tale_en" to myth.tale_en,
                    "Type" to myth.type,
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(myths)
        } catch (e: Exception) {
            emit(emptyList<MythDto>())
            e.printStackTrace()
        }
    }
}