package com.education.name.presentation.ui.screen.auth

import android.util.Log
import uk.ac.tees.mad.petcare.domain.model.User
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

    // 🔹 SignUp StateFlow (doesn't return user data)
    private val _signUpState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val signUpState: StateFlow<UiState<Unit>> = _signUpState

    // 🔹 SignIn StateFlow (returns logged-in User)
    private val _signInState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val signInState: StateFlow<UiState<User>> = _signInState

    // 🔸 SignUp
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = UiState.Loading
            val result = signUpUseCase(email, password)
            _signUpState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    // 🔸 SignIn
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = UiState.Loading
            val result = signInUseCase(email, password)
            Log.d("AUTH VIEWMODEL", result.toString())
            _signInState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    // 🔸 Optional: SignOut
    fun signOut() {
        // handle logout here (e.g., FirebaseAuth.signOut())
    }
}
