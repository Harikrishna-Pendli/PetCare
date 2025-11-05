package uk.ac.tees.mad.petcare.domain.repository

import uk.ac.tees.mad.petcare.domain.model.User


interface AuthRepository {
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun signIn(email: String, password: String): Result<User>
}
