package com.edsondev26.folkloripedia.domain
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryItems(collectionName: String): Flow<List<CategoryItemModel>>

    fun getDanceByID(documentId: String): Flow<DanceDetailModel?>
}