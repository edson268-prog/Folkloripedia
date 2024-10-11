package com.edsondev26.folkloripedia.data

import android.content.Context
import android.util.Log
import com.edsondev26.folkloripedia.domain.EventRepository
import com.edsondev26.folkloripedia.domain.model.EventModel
import com.edsondev26.folkloripedia.utils.LanguageUtils
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : EventRepository {
    private fun getCurrentLanguage(): String {
        return LanguageUtils.getSavedLanguage(context)
    }

    override fun getEventByMonth(monthRequired: String): Flow<List<EventModel>> = flow {
        try {
            val currentLanguage = getCurrentLanguage()
            Log.d("FirebaseFirestore", "month: $monthRequired")

            val result = firestore.collection("Events")
                .whereEqualTo("Month", monthRequired)
                .get()
                .await()

            val eventsList = result.map { document ->
                val id = document.id
                var name = document.getString("Name") ?: ""
                var description = document.getString("Description") ?: ""
                val img = document.getString("Image") ?: ""
                val month = document.getString("Month") ?: ""

                if (currentLanguage !== "es") {
                    name = document.getString("Name_$currentLanguage") ?: ""
                    description = document.getString("Description_$currentLanguage") ?: ""
                }
                Log.d("FirebaseFirestore", "Name: $name, img: $img")
                EventModel(name, description, img, month)
            }
            emit(eventsList)

        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}