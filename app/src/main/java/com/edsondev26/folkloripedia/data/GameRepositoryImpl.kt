package com.edsondev26.folkloripedia.data

import android.content.Context
import android.util.Log
import com.edsondev26.folkloripedia.domain.GameRepository
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.CuriosityModel
import com.edsondev26.folkloripedia.domain.model.QuizModel
import com.edsondev26.folkloripedia.utils.LanguageUtils
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : GameRepository {
    private fun getCurrentLanguage(): String {
        return LanguageUtils.getSavedLanguage(context)
    }

    override fun getCuriosityItem(curiosityId: String): Flow<CuriosityModel?> = flow {
        try {
            val currentLanguage = getCurrentLanguage()
            val id = "bolivia_curiosity_$curiosityId"

            val documentSnapshot = firestore.collection("Curiosities")
                .document(id)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val id = documentSnapshot.id
                var description = documentSnapshot.getString("Description") ?: ""
                val img = documentSnapshot.getString("Image") ?: ""

                if (currentLanguage !== "es") {
                    description = documentSnapshot.getString("Description_$currentLanguage") ?: ""
                }

                val curiosityItem = CuriosityModel(
                    id,
                    description,
                    img
                )
                emit(curiosityItem)
            } else {
                emit(null)
            }

        } catch (e: Exception) {
            emit(null)
        }
    }

    override fun getQuizItems(questions: Number): Flow<List<QuizModel>> = flow {
        try {
            val currentLanguage = getCurrentLanguage()

            val result = firestore.collection("Quiz")
                .get()
                .await()

            val questionsList = result.map { document ->
                val id = document.id
                var question = document.getString("Question") ?: ""
                val answerA = document.getString("Answer_A") ?: ""
                val answerB = document.getString("Answer_B") ?: ""
                val answerC = document.getString("Answer_C") ?: ""
                val rightAnswer = document.getString("Right_Answer") ?: ""
                val img = document.getString("Image") ?: ""
                val sound = document.getString("Sound") ?: ""

                if (currentLanguage !== "es") {
                    question = document.getString("Question_$currentLanguage") ?: ""
                }
                Log.d("FirebaseFirestore", "Question: $question, img: $img")
                QuizModel(id, question, answerA, answerB, answerC, rightAnswer, img, sound)
            }
            emit(questionsList)

        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}