package com.example.prophysiowearapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prophysiowearapp.network.ScheduleDto
import com.example.prophysiowearapp.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _schedules = MutableStateFlow<List<ScheduleDto>>(emptyList())
    val schedules: StateFlow<List<ScheduleDto>> = _schedules

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchSchedules()
    }

    fun fetchSchedules() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = scheduleRepository.getAvailableSchedules()
            result.onSuccess { data ->
                _schedules.value = data
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }
}
