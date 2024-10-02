package com.edsondev26.folkloripedia.data

import android.util.Log
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {

    override fun getCategoryItems(): Flow<List<CategoryItemInfo>> = flow {
        try {
            val result = firestore.collection("Dances")
                .get()
                .await()

            val danceList = result.map { document ->
                val img = document.getString("Image") ?: ""
                val name = document.getString("Name") ?: ""
                val region = document.getString("Region") ?: ""
                val year = document.getString("Year_Origin")?: ""

                Log.d("FirebaseFirestore", "Nombre: $name, img: $img")

                CategoryItemInfo(img, name, region, year)
            }

            emit(danceList)

        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}