package uk.ac.tees.mad.petcare.data.repository

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.petcare.data.datasource.remote.UserDataSource
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val realtime: FirebaseDatabase
) : UserRepository {

    override suspend fun createUser(user: User) {
        userDataSource.createUser(user)
    }

    override suspend fun getUserById(uid: String): User {
        val snapshot = realtime.getReference("users")
            .child(uid)
            .get()
            .await()
        return snapshot.getValue(User::class.java)
            ?: throw Exception("User not found")
    }

    override suspend fun updateNotifications(uid: String, enabled: Boolean) {
        realtime.getReference("users")
            .child(uid)
            .child("notifications")
            .setValue(enabled)
            .await()
    }
}
