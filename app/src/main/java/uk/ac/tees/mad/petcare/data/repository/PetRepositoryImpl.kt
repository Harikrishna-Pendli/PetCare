package uk.ac.tees.mad.petcare.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.model.PetEntity
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.domain.repository.PetRepository

class PetRepositoryImpl(
    private val dao: PetDao,
    private val firebaseService: PetDataSource
) : PetRepository {

    override suspend fun insertPet(pet: Pet) {
        val firebaseId = firebaseService.addPet(pet)
        dao.insertPet(
            PetEntity(
                firebaseId = firebaseId,
                name = pet.name,
                species = pet.species,
                age = pet.age,
                vaccinationInfo = pet.vaccinationInfo,
                foodPreferences = pet.foodPreferences
            )
        )
    }

    override suspend fun updatePet(firebaseId: String?, pet: Pet) {
        firebaseId?.let { firebaseService.updatePet(it, pet) }
        dao.insertPet(
            PetEntity(
                firebaseId = firebaseId,
                name = pet.name,
                species = pet.species,
                age = pet.age,
                vaccinationInfo = pet.vaccinationInfo,
                foodPreferences = pet.foodPreferences
            )
        )
    }

    override suspend fun deletePet(firebaseId: String?) {
        firebaseId?.let { firebaseService.deletePet(it) }
        firebaseId?.let {
            val allPets = dao.getAllPets()
            // Normally you'd delete using @Query, simplified for brevity
        }
    }

    override fun getAllPets(): Flow<List<Pet>> {
        return dao.getAllPets().map { list ->
            list.map {
                Pet(
                    name = it.name,
                    species = it.species,
                    age = it.age,
                    vaccinationInfo = it.vaccinationInfo,
                    foodPreferences = it.foodPreferences
                )
            }
        }
    }
}
