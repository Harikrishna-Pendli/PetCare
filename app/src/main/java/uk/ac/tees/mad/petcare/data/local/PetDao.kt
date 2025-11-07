package uk.ac.tees.mad.petcare.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.petcare.data.model.PetEntity

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<PetEntity>>

    @Query("SELECT * FROM pets WHERE firebaseId = :firebaseId LIMIT 1")
    suspend fun getPetByFirebaseId(firebaseId: String): PetEntity?

    @Query("SELECT * FROM pets WHERE localId = :localId LIMIT 1")
    suspend fun getPetByLocalId(localId: String): PetEntity?

    @Query("SELECT * FROM pets WHERE synced = 0 OR markedForDeletion = 1")
    suspend fun getUnsyncedPets(): List<PetEntity>

    @Query("DELETE FROM pets WHERE localId = :localId")
    suspend fun deleteByLocalId(localId: String)

}
