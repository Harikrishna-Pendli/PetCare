package uk.ac.tees.mad.petcare.data.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.petcare.domain.model.Pet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val realtime: FirebaseDatabase
) {

    private fun userPetsRef() =
        realtime.getReference("users/${auth.currentUser?.uid}/pets")

    // Add a new pet and return the Firestore document ID
    suspend fun addPet(pet: Pet): String {
        val key = userPetsRef().push().key ?: throw Exception("Key error")
        userPetsRef().child(key).setValue(pet.copy(firebaseId = key)).await()
        return key
    }

    // Update an existing pet by Firestore document ID
    suspend fun updatePet(petId: String, pet: Pet) {
        userPetsRef().child(petId).setValue(pet).await()
    }

    // Delete a pet by document ID
    suspend fun deletePet(petId: String) {
        userPetsRef().child(petId).removeValue().await()
    }

    // Fetch all pets
    suspend fun getAllPets(): List<Pair<String, Pet>> {
        val snapshot = userPetsRef().get().await()
        val list = mutableListOf<Pair<String, Pet>>()

        for (child in snapshot.children) {
            val pet = child.getValue(Pet::class.java)
            if (pet != null) {
                list.add(child.key!! to pet)
            }
        }
        return list
    }
}
