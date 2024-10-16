package com.edsondev26.folkloripedia.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.GameRepository
import com.edsondev26.folkloripedia.domain.model.CuriosityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteViewModel @Inject constructor(private val gameRepository: GameRepository): ViewModel() {
    private val _curiosityItem = MutableStateFlow<CuriosityModel?>(null)
    val curiosityItem: MutableStateFlow<CuriosityModel?> get() = _curiosityItem

    fun fetchCuriosityById(documentId: String) {
        viewModelScope.launch {
            gameRepository.getCuriosityItem(documentId).collect { item ->
                _curiosityItem.value = item
            }
        }
    }
}