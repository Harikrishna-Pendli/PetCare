package uk.ac.tees.mad.petcare.domain.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.petcare.domain.model.Pet

interface PetRepository {
    suspend fun insertPet(pet: Pet)
    suspend fun updatePet(firebaseId: String?, pet: Pet)
    suspend fun deletePet(firebaseId: String?)
    fun getAllPets(): Flow<List<Pet>>
}