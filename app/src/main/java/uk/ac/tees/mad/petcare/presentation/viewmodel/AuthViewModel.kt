package com.education.name.presentation.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.domain.use_case.SignInUseCase
import uk.ac.tees.mad.petcare.domain.use_case.SignUpUseCase
import uk.ac.tees.mad.petcare.util.UiState
import javax.inject.Inject
import kotlin.fold

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val authState: StateFlow<UiState<Unit>> = _authState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            val result = signUpUseCase(email, password)
            _authState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            val result = signInUseCase(email, password)
            Log.d("AUTH VIEWMODEL", result.toString())
            _authState.value = result.fold(
                onSuccess = { UiState.Success(result) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun signOut() {
        // optional, can call repository or add a SignOutUseCase
    }
}
