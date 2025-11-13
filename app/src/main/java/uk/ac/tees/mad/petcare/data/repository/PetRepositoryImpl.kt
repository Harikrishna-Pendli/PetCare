package uk.ac.tees.mad.petcare.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.model.PetEntity
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.domain.repository.PetRepository
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
    private val dao: PetDao,
    private val firebaseService: PetDataSource
) : PetRepository {

    override suspend fun insertPet(pet: Pet) {
        dao.insertPet(
            PetEntity(
                name = pet.name,
                species = pet.species,
                age = pet.age,
                vaccinationInfo = pet.vaccinationInfo,
                foodPreferences = pet.foodPreferences,
                synced = false
            )
        )
    }

    override suspend fun updatePet(firebaseId: String?, pet: Pet) {
        val existing = firebaseId?.let { dao.getPetByFirebaseId(it) }
        val localEntity = existing?.copy(
            name = pet.name,
            species = pet.species,
            age = pet.age,
            vaccinationInfo = pet.vaccinationInfo,
            foodPreferences = pet.foodPreferences,
            synced = false
        ) ?: PetEntity(
            firebaseId = firebaseId,
            name = pet.name,
            species = pet.species,
            age = pet.age,
            vaccinationInfo = pet.vaccinationInfo,
            foodPreferences = pet.foodPreferences,
            synced = false
        )
        dao.insertPet(localEntity)
    }

    override suspend fun deletePet(firebaseId: String?) {
        if (firebaseId != null) {
            val pet = dao.getPetByFirebaseId(firebaseId)
            pet?.let {
                dao.insertPet(it.copy(markedForDeletion = true, synced = false))
            }
        }
    }

    override fun getAllPets(): Flow<List<Pet>> {
        return dao.getAllPets().map { list ->
            list.filter { !it.markedForDeletion }.map {
                Pet(
                    name = it.name,
                    species = it.species,
                    age = it.age,
                    vaccinationInfo = it.vaccinationInfo.toString(),
                    foodPreferences = it.foodPreferences.toString()
                )
            }
        }
    }
}

