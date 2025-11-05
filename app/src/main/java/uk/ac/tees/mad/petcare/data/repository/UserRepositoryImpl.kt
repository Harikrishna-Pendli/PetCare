package uk.ac.tees.mad.petcare.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.petcare.data.datasource.UserDataSource
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.domain.repository.UserRepository
import javax.inject.Inject
import kotlin.jvm.java

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val firestore: FirebaseFirestore
): UserRepository {
    override suspend fun createUser(user: User) {
        userDataSource.createUser(user)
    }

    override suspend fun getUserById(email: String): User {
        val snapshot = firestore.collection("users").document(email).get().await()
        return snapshot.toObject(User::class.java)
            ?: throw Exception("User not found")
    }
}
