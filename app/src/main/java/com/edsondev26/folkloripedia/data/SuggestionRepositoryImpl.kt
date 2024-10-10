package com.edsondev26.folkloripedia.data

import android.content.Context
import android.provider.Settings
import com.edsondev26.folkloripedia.domain.SuggestionRepository
import com.edsondev26.folkloripedia.domain.model.SuggestionModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SuggestionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : SuggestionRepository {

    companion object {
        private const val LAST_SUGGESTION_TIME = "last_suggestion_time"
        private const val TIME_LIMIT = 24 * 60 * 60 * 1000
//        private const val TIME_LIMIT = 120
    }

    override fun setSuggestion(suggestion: SuggestionModel): Flow<String> = flow {
        try {
            val androidId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

            val suggestionData = hashMapOf(
                "name" to suggestion.name,
                "email" to suggestion.email,
                "suggestion" to suggestion.suggestion,
                "deviceId" to androidId,
                "dateTime" to FieldValue.serverTimestamp()
            )

            val documentReference = firestore.collection("Suggestions")
                .add(suggestionData)
                .await()

            updateLastSuggestionTime()

            emit(documentReference.id)
        } catch (e: Exception) {
            emit("Error: ${e.message}")
        }
    }

    override fun isAllowedToSend(): Flow<Boolean> = flow {
        val lastSuggestionTime = getLastSuggestionTime()
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastSuggestionTime > TIME_LIMIT) {
            emit(true)
        } else {
            emit(false)
        }
    }

    private fun getLastSuggestionTime(): Long {
        val sharedPreferences =
            context.getSharedPreferences("SuggestionPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getLong(LAST_SUGGESTION_TIME, 0)
    }

    private fun updateLastSuggestionTime() {
        val sharedPreferences =
            context.getSharedPreferences("SuggestionPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putLong(LAST_SUGGESTION_TIME, System.currentTimeMillis())
            apply()
        }
    }
}