package com.edsondev26.folkloripedia.ui.populate.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.PopulateRepository
import com.edsondev26.folkloripedia.domain.dto.DanceDto
import com.edsondev26.folkloripedia.domain.dto.EventDto
import com.edsondev26.folkloripedia.domain.dto.MythDto
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
}