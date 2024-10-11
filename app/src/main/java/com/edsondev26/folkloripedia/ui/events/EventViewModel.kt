package com.edsondev26.folkloripedia.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.EventRepository
import com.edsondev26.folkloripedia.domain.model.EventModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepository: EventRepository):
    ViewModel() {
    private var _eventItems = MutableStateFlow<List<EventModel>>(emptyList())
    val eventItems: StateFlow<List<EventModel>> = _eventItems

    private var _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchEvents(month: String) {
        _isLoading.value = true
        viewModelScope.launch {
            eventRepository.getEventByMonth(month).collect { items ->
                _eventItems.value = items
                _isLoading.value = false
            }
        }
    }
}