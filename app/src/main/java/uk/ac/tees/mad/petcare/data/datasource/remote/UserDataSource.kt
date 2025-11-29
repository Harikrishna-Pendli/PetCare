package uk.ac.tees.mad.petcare.data.datasource.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import uk.ac.tees.mad.petcare.domain.model.User
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val realtime: FirebaseDatabase
) {
    fun createUser(user: User) {
        val uid = auth.currentUser?.uid ?: return
        realtime.getReference("users")
            .child(uid)
            .setValue(user)
            .addOnSuccessListener { Log.d("USER", "User saved to Realtime DB") }
            .addOnFailureListener { e -> Log.e("USER", "Fail: ${e.message}") }
    }
}