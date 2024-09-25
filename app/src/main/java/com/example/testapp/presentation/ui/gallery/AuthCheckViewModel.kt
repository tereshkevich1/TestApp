package com.example.testapp.presentation.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.use_case.authentication.IsUserAuthenticatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthCheckViewModel @Inject constructor(private val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase) :
    ViewModel() {

    private var _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
            val isAuthenticated = isUserAuthenticatedUseCase.invoke()
            _isAuthenticated.value = isAuthenticated
        }
    }
}