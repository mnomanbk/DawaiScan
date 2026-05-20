package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.Medicine
import com.example.data.MedicineRepository
import com.example.data.ScanHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DawaiViewModel(private val repository: MedicineRepository) : ViewModel() {

    val scanHistory: StateFlow<List<ScanHistory>> = repository.scanHistory
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentScanResult = MutableStateFlow<Medicine?>(null)
    val currentScanResult: StateFlow<Medicine?> = _currentScanResult.asStateFlow()
    
    private val _scanLoading = MutableStateFlow(false)
    val scanLoading: StateFlow<Boolean> = _scanLoading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.populateInitialData()
        }
    }

    fun verifyCode(code: String) {
        viewModelScope.launch {
            _scanLoading.value = true
            // Simulate network/AI delay
            kotlinx.coroutines.delay(1500)
            val result = repository.verifyMedicine(code)
            _currentScanResult.value = result
            _scanLoading.value = false
        }
    }
    
    fun clearScanResult() {
        _currentScanResult.value = null
    }
}

class DawaiViewModelFactory(private val repository: MedicineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DawaiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DawaiViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
