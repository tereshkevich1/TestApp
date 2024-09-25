package com.example.testapp.presentation.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.repository.LocationResult
import com.example.testapp.domain.use_case.location.GetCurrentLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _location = MutableLiveData<LocationResult>()
    val location: LiveData<LocationResult> get() = _location

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            val result = getCurrentLocationUseCase()
            _location.value = result
        }
    }
}