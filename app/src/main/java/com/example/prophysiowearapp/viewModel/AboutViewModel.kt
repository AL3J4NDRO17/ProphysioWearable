package com.example.prophysiowearapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prophysiowearapp.network.ClinicInfoResponse
import com.example.prophysiowearapp.repository.ClinicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val clinicRepository: ClinicRepository
) : ViewModel() {

    private val _clinicInfo = MutableStateFlow<ClinicInfoResponse?>(null)
    val clinicInfo: StateFlow<ClinicInfoResponse?> = _clinicInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchClinicInfo()
    }

    fun fetchClinicInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = clinicRepository.getClinicInfo()
            result.onSuccess { info ->
                _clinicInfo.value = info
            }.onFailure { e ->
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }
}
