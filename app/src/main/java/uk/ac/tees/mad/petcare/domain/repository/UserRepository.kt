package uk.ac.tees.mad.petcare.domain.repository

import uk.ac.tees.mad.petcare.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun getUserById(email: String): User
}