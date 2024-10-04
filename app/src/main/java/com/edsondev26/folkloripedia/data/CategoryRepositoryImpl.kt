package com.edsondev26.folkloripedia.data

import android.content.Context
import android.util.Log
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.ArticleModel
import com.edsondev26.folkloripedia.domain.model.ArticleModel.*
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import com.edsondev26.folkloripedia.utils.LanguageUtils
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : CategoryRepository {
    private fun getCurrentLanguage(): String {
        return LanguageUtils.getSavedLanguage(context)
    }


    override fun getCategoryItems(collectionName: String): Flow<List<CategoryItemModel>> = flow {
        try {
            val result = firestore.collection(collectionName)
                .get()
                .await()

            val danceList = result.map { document ->
                val id = document.id
                val img = document.getString("Image") ?: ""
                val name = document.getString("Name") ?: ""
                val type = document.getString("Type") ?: ""

                val info = document.getString(getInfoValue(collectionName)) ?: ""

//                Log.d("FirebaseFirestore", "Nombre: $name, img: $img")
                CategoryItemModel(id, name, type, img, info)
            }
            emit(danceList)

        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getDanceByID(documentId: String): Flow<DanceDetailModel?> = flow {
        try {
            val currentLanguage = getCurrentLanguage()

            val documentSnapshot = firestore.collection("Dances")
                .document(documentId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val id = documentSnapshot.id
                val name = documentSnapshot.getString("Name") ?: ""
                val region = documentSnapshot.getString("Region") ?: ""
                var description = documentSnapshot.getString("Description") ?: ""
                var instruments = documentSnapshot.getString("Instruments") ?: ""
                val vestment = documentSnapshot.getString("Vestment") ?: ""
                val img = documentSnapshot.getString("Image") ?: ""
                val year = documentSnapshot.getString("Year_Origin") ?: ""

                Log.d("LANGUAGE_UTIL", "The language is: $currentLanguage")
                if (currentLanguage !== "es") {
                    description = documentSnapshot.getString("Description_$currentLanguage") ?: ""
                    instruments = documentSnapshot.getString("Instruments_$currentLanguage") ?: ""
                }

                val danceItem = DanceDetailModel(
                    id,
                    name,
                    region,
                    description,
                    instruments,
                    vestment,
                    img,
                    year
                )
                emit(danceItem)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    fun getInfoValue(collectionName: String): String {
        val articleModel = try {
            ArticleModel.valueOf(collectionName)
        } catch (e: IllegalArgumentException) {
            null
        }

        when (articleModel) {
            Dances -> {
                return "Region"
            }
            Music -> {
                return "Name"
            }
            Arts -> {
                return "Author"
            }
            Myths -> {
                return "Name"
            }
            null -> {
                return ""
            }
        }
    }
}