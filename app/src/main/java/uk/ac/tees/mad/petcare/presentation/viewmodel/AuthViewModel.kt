package uk.ac.tees.mad.petcare.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.domain.repository.AuthRepository
import uk.ac.tees.mad.petcare.util.UiState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val signUpState: StateFlow<UiState<Unit>> = _signUpState

    private val _signInState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val signInState: StateFlow<UiState<User>> = _signInState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = UiState.Loading
            val result = authRepository.signUp(email, password)
            _signUpState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = UiState.Loading
            val result = authRepository.login(email, password)
            Log.d("AUTH VIEWMODEL", result.toString())
            _signInState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}
