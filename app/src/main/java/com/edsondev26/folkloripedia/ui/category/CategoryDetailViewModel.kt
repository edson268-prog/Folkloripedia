package com.edsondev26.folkloripedia.ui.category

import androidx.lifecycle.ViewModel
import com.edsondev26.folkloripedia.data.providers.CategoryItemProvider
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(categoryItemProvider: CategoryItemProvider):
    ViewModel() {
    private var _categoryItems = MutableStateFlow<List<CategoryItemInfo>>(emptyList())
    val categoryItems: StateFlow<List<CategoryItemInfo>> = _categoryItems

    init {
        _categoryItems.value = categoryItemProvider.getArticleItems()
    }

}