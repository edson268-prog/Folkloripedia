package com.edsondev26.folkloripedia.ui.category_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailFragmentViewModel @Inject constructor(private val categoryRepository: CategoryRepository): ViewModel() {
    private val _danceItem = MutableStateFlow<DanceDetailModel?>(null)
    val danceItem: MutableStateFlow<DanceDetailModel?> get() = _danceItem

    private var _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchDanceById(documentId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getDanceByID(documentId).collect { item ->
                _danceItem.value = item
                _isLoading.value = false
            }
        }
    }
}