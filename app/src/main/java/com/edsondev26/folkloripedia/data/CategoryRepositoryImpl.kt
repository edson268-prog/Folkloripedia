package com.edsondev26.folkloripedia.data

import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {

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

//                Log.d("FirebaseFirestore", "Nombre: $name, img: $img")
                CategoryItemModel(id, name, type, img)
            }
            emit(danceList)

        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getDancebyID(documentId: String): Flow<DanceDetailModel?> = flow {
        try {
            val documentSnapshot = firestore.collection("Dances")
                .document(documentId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val id = documentSnapshot.id
                val name = documentSnapshot.getString("Name") ?: ""
                val region = documentSnapshot.getString("Region") ?: ""
                val description = documentSnapshot.getString("Description") ?: ""
                val instruments = documentSnapshot.getString("Instruments") ?: ""
                val vestment = documentSnapshot.getString("Vestment") ?: ""
                val img = documentSnapshot.getString("Image") ?: ""
                val year = documentSnapshot.getString("Year_Origin") ?: ""

                val danceItem = DanceDetailModel(id, name, region, description, instruments,vestment, img, year)
                emit(danceItem)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }
}