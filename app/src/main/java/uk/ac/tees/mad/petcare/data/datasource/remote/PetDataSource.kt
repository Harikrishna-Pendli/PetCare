package uk.ac.tees.mad.petcare.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.petcare.domain.model.Pet

class PetDataSource(firebaseFirestore: FirebaseFirestore) {

    private val db = firebaseFirestore.collection("pets")

    // Add a new pet and return the Firestore document ID
    suspend fun addPet(pet: Pet): String {
        val docRef = db.add(pet).await()
        return docRef.id
    }

    // Update an existing pet by Firestore document ID
    suspend fun updatePet(petId: String, pet: Pet) {
        db.document(petId).set(pet).await()
    }

    // Delete a pet by document ID
    suspend fun deletePet(petId: String) {
        db.document(petId).delete().await()
    }

    // Fetch all pets
    suspend fun getAllPets(): List<Pair<String, Pet>> {
        val snapshot = db.get().await()
        return snapshot.documents.mapNotNull { doc ->
            val pet = doc.toObject(Pet::class.java)
            if (pet != null) doc.id to pet else null
        }
    }
}
