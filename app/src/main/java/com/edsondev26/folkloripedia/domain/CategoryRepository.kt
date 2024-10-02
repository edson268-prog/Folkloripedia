package com.edsondev26.folkloripedia.domain
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryItems(): Flow<List<CategoryItemInfo>>
}