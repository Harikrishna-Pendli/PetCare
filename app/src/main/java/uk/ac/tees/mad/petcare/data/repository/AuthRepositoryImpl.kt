package uk.ac.tees.mad.petcare.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> = try {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val user = result.user ?: return Result.failure(Exception("Login failed"))

        Result.success(User(email = user.email ?: ""))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signUp(email: String, password: String): Result<User> = try {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = result.user ?: return Result.failure(Exception("Signup failed"))

        val user = User(name = "", email = firebaseUser.email ?: "")

        // Save user under UID
        firestore.collection("users")
            .document(firebaseUser.uid)
            .set(user)
            .await()

        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun currentUser(): User? {
        val u = firebaseAuth.currentUser
        return u?.let { User(email = it.email ?: "") }
    }

    override fun checkIfUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
