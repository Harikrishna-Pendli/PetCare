package uk.ac.tees.mad.petcare.domain.repository

import uk.ac.tees.mad.petcare.domain.model.User


interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun currentUser(): User?
    fun checkIfUserLoggedIn(): Boolean
}
