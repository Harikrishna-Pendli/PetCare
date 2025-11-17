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
import uk.ac.tees.mad.petcare.data.model.toDomainModel
import uk.ac.tees.mad.petcare.data.model.PetEntity

@HiltWorker
class PetSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dao: PetDao,
    private val firebaseService: PetDataSource
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val unsynced = dao.getUnsyncedPets()
            for (entity in unsynced) {
                syncOne(entity)
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private suspend fun syncOne(pet: PetEntity) {
        try {
            when {
                pet.markedForDeletion -> {
                    val fid = pet.firebaseId
                    if (fid != null) {
                        firebaseService.deletePet(fid)
                    }
                    // delete locally
                    dao.deleteByLocalId(pet.localId)
                }
                pet.firebaseId == null -> {
                    // add to firestore
                    val id = firebaseService.addPet(pet.toDomainModel())
                    dao.insertPet(pet.copy(firebaseId = id, synced = true))
                }
                else -> {
                    // update existing on firestore
                    firebaseService.updatePet(pet.firebaseId, pet.toDomainModel())
                    dao.insertPet(pet.copy(synced = true))
                }
            }
        } catch (e: Exception) {
            // do nothing, leave unsynced
        }
    }
}
