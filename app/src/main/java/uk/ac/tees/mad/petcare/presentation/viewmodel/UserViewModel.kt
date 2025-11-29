package uk.ac.tees.mad.petcare.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadUser() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            _loading.value = true
            try {
                val fetchedUser = userRepository.getUserById(uid)
                _user.value = fetchedUser
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateName(newName: String) {
        val firebaseUser = auth.currentUser ?: return
        val uid = firebaseUser.uid
        val email = firebaseUser.email ?: ""
        viewModelScope.launch {
            val updatedUser = User(name = newName, email = email)
            userRepository.createUser(updatedUser)
            _user.value = updatedUser
        }
    }

    fun updateNotificationToggle(enabled: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepository.updateNotifications(uid, enabled)
            _user.value = _user.value.copy(notifications = enabled)
        }
    }

    fun logout(onDone: () -> Unit) {
        auth.signOut()
        onDone()
    }
}