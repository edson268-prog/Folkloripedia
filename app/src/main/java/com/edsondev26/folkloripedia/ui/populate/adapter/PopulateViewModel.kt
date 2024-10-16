package com.edsondev26.folkloripedia.ui.populate.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.PopulateRepository
import com.edsondev26.folkloripedia.domain.dto.ArtDto
import com.edsondev26.folkloripedia.domain.dto.CuriosityDto
import com.edsondev26.folkloripedia.domain.dto.DanceDto
import com.edsondev26.folkloripedia.domain.dto.EventDto
import com.edsondev26.folkloripedia.domain.dto.MusicDto
import com.edsondev26.folkloripedia.domain.dto.MythDto
import com.edsondev26.folkloripedia.domain.dto.QuizDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopulateViewModel @Inject constructor(private val populateRepository: PopulateRepository) : ViewModel() {
    private val _eventItem = MutableStateFlow<List<EventDto>>(emptyList())
    val eventItem: StateFlow<List<EventDto>> = _eventItem.asStateFlow()

    private val _danceItem = MutableStateFlow<List<DanceDto>>(emptyList())
    val danceItem: StateFlow<List<DanceDto>> = _danceItem.asStateFlow()

    private val _mythItem = MutableStateFlow<List<MythDto>>(emptyList())
    val mythItem: StateFlow<List<MythDto>> = _mythItem.asStateFlow()

    private val _curiosityItem = MutableStateFlow<List<CuriosityDto>>(emptyList())
    val curiosityItem: StateFlow<List<CuriosityDto>> = _curiosityItem.asStateFlow()

    private val _musicItem = MutableStateFlow<List<MusicDto>>(emptyList())
    val musicItem: StateFlow<List<MusicDto>> = _musicItem.asStateFlow()

    private val _artItem = MutableStateFlow<List<ArtDto>>(emptyList())
    val artItem: StateFlow<List<ArtDto>> = _artItem.asStateFlow()

    private val _quizItem = MutableStateFlow<List<QuizDto>>(emptyList())
    val quizItem: StateFlow<List<QuizDto>> = _quizItem.asStateFlow()

    fun fetchInsertEvents(events: List<EventDto>) {
        viewModelScope.launch {
            populateRepository.insertEvents(events).collect { item ->
                _eventItem.value = item
            }
        }
    }

    fun fetchInsertDances(dances: List<DanceDto>) {
        viewModelScope.launch {
            populateRepository.insertDances(dances).collect { item ->
                _danceItem.value = item
            }
        }
    }

    fun fetchInsertMyths(myths: List<MythDto>) {
        viewModelScope.launch {
            populateRepository.insertMyths(myths).collect { item ->
                _mythItem.value = item
            }
        }
    }

    fun fetchInsertCuriosities(curiosities: List<CuriosityDto>) {
        viewModelScope.launch {
            populateRepository.insertCuriosities(curiosities).collect { item ->
                _curiosityItem.value = item
            }
        }
    }

    fun fetchInsertMusic(music: List<MusicDto>) {
        viewModelScope.launch {
            populateRepository.insertMusic(music).collect { item ->
                _musicItem.value = item
            }
        }
    }

    fun fetchInsertArt(art: List<ArtDto>) {
        viewModelScope.launch {
            populateRepository.insertArt(art).collect { item ->
                _artItem.value = item
            }
        }
    }

    fun fetchInsertQuiz(quiz: List<QuizDto>) {
        viewModelScope.launch {
            populateRepository.insertQuiz(quiz).collect { item ->
                _quizItem.value = item
            }
        }
    }
}