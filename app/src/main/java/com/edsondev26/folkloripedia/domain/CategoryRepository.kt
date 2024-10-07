package com.edsondev26.folkloripedia.domain
import com.edsondev26.folkloripedia.domain.model.ArtDetailModel
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import com.edsondev26.folkloripedia.domain.model.MusicDetailModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryItems(collectionName: String): Flow<List<CategoryItemModel>>

    fun getDanceByID(documentId: String): Flow<DanceDetailModel?>

    fun getArtByID(documentId: String): Flow<ArtDetailModel?>

    fun getMusicByID(documentId: String): Flow<MusicDetailModel?>
}