package com.edsondev26.folkloripedia.data

import android.content.Context
import android.util.Log
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.ArtDetailModel
import com.edsondev26.folkloripedia.domain.model.ArticleModel
import com.edsondev26.folkloripedia.domain.model.ArticleModel.*
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import com.edsondev26.folkloripedia.domain.model.MusicDetailModel
import com.edsondev26.folkloripedia.domain.model.MythDetailModel
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

            val itemsList = result.map { document ->
                val id = document.id
                val img = document.getString("Image") ?: ""
                val name = document.getString("Name") ?: ""
                val type = document.getString("Type") ?: ""

                val info = document.getString(getInfoValue(collectionName)) ?: ""

//                Log.d("FirebaseFirestore", "Nombre: $name, img: $img")
                CategoryItemModel(id, name, type, img, info)
            }
            emit(itemsList)

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
                if (currentLanguage != "es") {
                    description = documentSnapshot.getString("Description_$currentLanguage") ?: ""
                    instruments = documentSnapshot.getString("Instruments_$currentLanguage") ?: ""
                }
                Log.d("LANGUAGE_UTIL", "The description is: $description")

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

    override fun getArtByID(documentId: String): Flow<ArtDetailModel?> = flow {
        try {
            val currentLanguage = getCurrentLanguage()

            val documentSnapshot = firestore.collection("Arts")
                .document(documentId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val id = documentSnapshot.id
                val name = documentSnapshot.getString("Name") ?: ""
                val author = documentSnapshot.getString("Author") ?: ""
                var description = documentSnapshot.getString("Description") ?: ""
                val img = documentSnapshot.getString("Image") ?: ""
                var material = documentSnapshot.getString("Material") ?: ""

                if (currentLanguage != "es") {
                    description = documentSnapshot.getString("Description_$currentLanguage") ?: ""
                    material = documentSnapshot.getString("Material_$currentLanguage") ?: ""
                }

                val artItem = ArtDetailModel(
                    id,
                    name,
                    author,
                    description,
                    material,
                    img
                )
                emit(artItem)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    override fun getMusicByID(documentId: String): Flow<MusicDetailModel?> = flow {
        try {
            val currentLanguage = getCurrentLanguage()

            val documentSnapshot = firestore.collection("Music")
                .document(documentId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val id = documentSnapshot.id
                val name = documentSnapshot.getString("Name") ?: ""
                val category = documentSnapshot.getString("Category") ?: ""
                var description = documentSnapshot.getString("Description") ?: ""
                var material = documentSnapshot.getString("Material") ?: ""
                val origin = documentSnapshot.getString("Origin") ?: ""
                val img = documentSnapshot.getString("Image") ?: ""
                val sound = documentSnapshot.getString("Sound") ?: ""

                if (currentLanguage != "es") {
                    description = documentSnapshot.getString("Description_$currentLanguage") ?: ""
                    material = documentSnapshot.getString("Material_$currentLanguage") ?: ""
                }

                val musicItem = MusicDetailModel(
                    id,
                    name,
                    category,
                    description,
                    material,
                    origin,
                    img,
                    sound
                )
                emit(musicItem)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    override fun getMythByID(documentId: String): Flow<MythDetailModel?> = flow {
        try {
            val currentLanguage = getCurrentLanguage()

            val documentSnapshot = firestore.collection("Myths")
                .document(documentId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val id = documentSnapshot.id
                val name = documentSnapshot.getString("Name") ?: ""
                val origin = documentSnapshot.getString("Origin") ?: ""
                var tale = documentSnapshot.getString("Tale") ?: ""
                val img = documentSnapshot.getString("Image") ?: ""

                if (currentLanguage != "es") {
                    tale = documentSnapshot.getString("Tale_$currentLanguage") ?: ""
                }

                val mythItem = MythDetailModel(
                    id,
                    name,
                    origin,
                    tale,
                    img,
                )
                emit(mythItem)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    private fun getInfoValue(collectionName: String): String {
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
                return "Origin"
            }
            Arts -> {
                return "Author"
            }
            Myths -> {
                return "Origin"
            }
            null -> {
                return ""
            }
        }
    }
}