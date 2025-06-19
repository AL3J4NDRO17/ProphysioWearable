package com.example.prophysiowearapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prophysiowearapp.network.ServiceDto
import com.example.prophysiowearapp.repository.ServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val servicesRepository: ServicesRepository
) : ViewModel() {

    private val _services = MutableStateFlow<List<ServiceDto>>(emptyList())
    val services: StateFlow<List<ServiceDto>> = _services

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchServices()
    }

    fun fetchServices() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = servicesRepository.getServices()
            result.onSuccess { data ->
                _services.value = data
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }
}
