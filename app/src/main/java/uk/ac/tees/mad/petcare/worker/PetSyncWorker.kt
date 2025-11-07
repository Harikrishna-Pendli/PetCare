package uk.ac.tees.mad.petcare.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.model.PetEntity
import uk.ac.tees.mad.petcare.data.model.toDomainModel

@HiltWorker
class PetSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dao: PetDao,
    private val firebaseService: PetDataSource
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val unsyncedPets = dao.getUnsyncedPets()
            for (pet in unsyncedPets) {
                syncPet(pet)
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private suspend fun syncPet(pet: PetEntity) {
        when {
            pet.markedForDeletion -> {
                pet.firebaseId?.let { firebaseService.deletePet(it) }
                dao.deleteByLocalId(pet.localId)
            }

            pet.firebaseId == null -> {
                val firebaseId = firebaseService.addPet(pet.toDomainModel())
                dao.insertPet(pet.copy(firebaseId = firebaseId, synced = true))
            }

            else -> {
                firebaseService.updatePet(pet.firebaseId, pet.toDomainModel())
                dao.insertPet(pet.copy(synced = true))
            }
        }
    }
}
