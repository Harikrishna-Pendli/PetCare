package uk.ac.tees.mad.petcare.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.model.PetEntity
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.domain.repository.PetRepository
import java.util.UUID
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
    private val dao: PetDao,
    private val firebase: PetDataSource
) : PetRepository {

    override suspend fun insertPet(pet: Pet) {
        dao.insertPet(
            PetEntity(
                localId = pet.localId,
                firebaseId = pet.firebaseId,
                name = pet.name,
                species = pet.species,
                age = pet.age,
                vaccinationInfo = pet.vaccinationInfo,
                foodPreferences = pet.foodPreferences,
                synced = false,
                markedForDeletion = false
            )
        )
        try {
            val id = firebase.addPet(pet)
            val existing = dao.getPetByLocalId(pet.localId)?.copy(firebaseId = id, synced = true)
            existing?.let {
                dao.insertPet(it.copy(firebaseId = id, synced = true))
            }
            if (existing != null) dao.insertPet(existing)
        } catch (e: Exception) {
            // worker will sync later
        }
    }

    override suspend fun updatePet(localId: Int?, pet: Pet) {
        if (localId == null) return
        val existing = dao.getPetByLocalId(localId)
        if (existing != null) {
            val new = existing.copy(
                name = pet.name,
                species = pet.species,
                age = pet.age,
                vaccinationInfo = pet.vaccinationInfo,
                foodPreferences = pet.foodPreferences,
                synced = false
            )
            dao.insertPet(new)
            try {
                new.firebaseId?.let { firebase.updatePet(it, pet) ?: Unit }
                dao.insertPet(new.copy(synced = true))
            } catch (_: Exception) {
                // let worker handle later
            }
        } else {
            insertPet(pet)
        }
    }

    override suspend fun deletePet(localId: Int?) {
        if (localId == null) return
        val existing = dao.getPetByLocalId(localId)
        existing?.let {
            // mark for deletion locally; worker will delete from Firestore if firebaseId present
            dao.insertPet(it.copy(markedForDeletion = true, synced = false))
        }
    }

    override fun getAllPets(): Flow<List<Pet>> {
        return dao.getAllPets().map { list ->
            list.filter { !it.markedForDeletion }.map {
                Pet(
                    localId = it.localId,
                    firebaseId = it.firebaseId,
                    name = it.name,
                    species = it.species,
                    age = it.age,
                    vaccinationInfo = it.vaccinationInfo ?: "",
                    foodPreferences = it.foodPreferences ?: ""
                )
            }
        }
    }
}

