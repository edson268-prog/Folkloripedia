package com.edsondev26.folkloripedia.ui.category_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.ArtDetailModel
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import com.edsondev26.folkloripedia.domain.model.MusicDetailModel
import com.edsondev26.folkloripedia.domain.model.MythDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailFragmentViewModel @Inject constructor(private val categoryRepository: CategoryRepository): ViewModel() {
    private val _danceItem = MutableStateFlow<DanceDetailModel?>(null)
    val danceItem: MutableStateFlow<DanceDetailModel?> get() = _danceItem

    private val _artItem = MutableStateFlow<ArtDetailModel?>(null)
    val artItem: MutableStateFlow<ArtDetailModel?> get() = _artItem

    private val _musicItem = MutableStateFlow<MusicDetailModel?>(null)
    val musicItem: MutableStateFlow<MusicDetailModel?> get() = _musicItem

    private val _mythItem = MutableStateFlow<MythDetailModel?>(null)
    val mythItem: MutableStateFlow<MythDetailModel?> get() = _mythItem

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

    fun fetchArtById(documentId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getArtByID(documentId).collect { item ->
                _artItem.value = item
                _isLoading.value = false
            }
        }
    }

    fun fetchMusicById(documentId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getMusicByID(documentId).collect { item ->
                _musicItem.value = item
                _isLoading.value = false
            }
        }
    }

    fun fetchMythById(documentId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getMythByID(documentId).collect { item ->
                _mythItem.value = item
                _isLoading.value = false
            }
        }
    }
}