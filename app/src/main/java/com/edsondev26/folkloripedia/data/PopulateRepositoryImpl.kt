package com.edsondev26.folkloripedia.data

import android.content.Context
import android.util.Log
import com.edsondev26.folkloripedia.domain.PopulateRepository
import com.edsondev26.folkloripedia.domain.dto.ArtDto
import com.edsondev26.folkloripedia.domain.dto.CuriosityDto
import com.edsondev26.folkloripedia.domain.dto.DanceDto
import com.edsondev26.folkloripedia.domain.dto.EventDto
import com.edsondev26.folkloripedia.domain.dto.MusicDto
import com.edsondev26.folkloripedia.domain.dto.MythDto
import com.edsondev26.folkloripedia.domain.dto.QuizDto
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

    override fun insertCuriosities(curiosities: List<CuriosityDto>): Flow<List<CuriosityDto>> = flow {
        try {
            val batch = firestore.batch()

            curiosities.forEach { curiosity ->
                val docName = "bolivia_curiosity_${curiosity.id}"
                val eventRef = firestore.collection("Curiosities").document(docName)
                val eventData = hashMapOf(
                    "Description" to curiosity.description,
                    "Description_en" to curiosity.description_en,
                    "Image" to curiosity.img
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(curiosities)
        } catch (e: Exception) {
            emit(emptyList<CuriosityDto>())
            e.printStackTrace()
        }
    }

    override fun insertMusic(music: List<MusicDto>): Flow<List<MusicDto>> = flow {
        try {
            val batch = firestore.batch()

            music.forEach { music ->
                val docName = "bolivia_musica_${music.id}"
                val eventRef = firestore.collection("Music").document(docName)
                val eventData = hashMapOf(
                    "Category" to music.category,
                    "Description" to music.description,
                    "Description_en" to music.description_en,
                    "Image" to music.img,
                    "Material" to music.material,
                    "Material_en" to music.material_en,
                    "Name" to music.name,
                    "Origin" to music.origin,
                    "Sound" to music.sound,
                    "Type" to music.type
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(music)
        } catch (e: Exception) {
            emit(emptyList<MusicDto>())
            e.printStackTrace()
        }
    }

    override fun insertArt(art: List<ArtDto>): Flow<List<ArtDto>> = flow {
        try {
            val batch = firestore.batch()

            art.forEach { art ->
                val docName = "bolivia_art_${art.id}"
                val eventRef = firestore.collection("Arts").document(docName)
                val eventData = hashMapOf(
                    "Author" to art.author,
                    "Description" to art.description,
                    "Description_en" to art.description_en,
                    "Image" to art.img,
                    "Material" to art.material,
                    "Material_en" to art.material_en,
                    "Name" to art.name,
                    "Type" to art.type
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(art)
        } catch (e: Exception) {
            emit(emptyList<ArtDto>())
            e.printStackTrace()
        }
    }

    override fun insertQuiz(quiz: List<QuizDto>): Flow<List<QuizDto>> = flow {
        try {
            val batch = firestore.batch()

            quiz.forEach { quiz ->
                val docName = "quiz_${quiz.id}"
                val eventRef = firestore.collection("Quiz").document(docName)
                val eventData = hashMapOf(
                    "Answer_A" to quiz.answerA,
                    "Answer_B" to quiz.answerB,
                    "Answer_C" to quiz.answerC,
                    "Image" to quiz.img,
                    "Question" to quiz.question,
                    "Question_en" to quiz.question_en,
                    "Right_Answer" to quiz.right_answer,
                    "Sound" to quiz.sound
                )
                batch.set(eventRef, eventData)
            }

            batch.commit().await()

            emit(quiz)
        } catch (e: Exception) {
            emit(emptyList<QuizDto>())
            e.printStackTrace()
        }
    }
}