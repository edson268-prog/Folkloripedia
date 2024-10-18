package com.edsondev26.folkloripedia.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(private val categoryRepository: CategoryRepository):
    ViewModel() {
    private var _categoryItems = MutableStateFlow<List<CategoryItemModel>>(emptyList())
    val categoryItems: MutableStateFlow<List<CategoryItemModel>> = _categoryItems

    private var _isLoading = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun fetchCategoryItems(collectionName: String) {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getCategoryItems(collectionName).collect { items ->
                _categoryItems.value = items
                _isLoading.value = false
            }
        }
    }
}