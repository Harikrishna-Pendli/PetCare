package uk.ac.tees.mad.petcare.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.ac.tees.mad.petcare.domain.model.Pet

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val firebaseId: String? = null,
    val name: String,
    val species: String,
    val age: Int,
    val vaccinationInfo: String? = null,
    val foodPreferences: String? = null,
    val synced: Boolean = false,
    val markedForDeletion: Boolean = false
)

fun PetEntity.toDomainModel() = uk.ac.tees.mad.petcare.domain.model.Pet(
    localId = localId,
    firebaseId = firebaseId,
    name = name,
    species = species,
    age = age,
    vaccinationInfo = vaccinationInfo ?: "",
    foodPreferences = foodPreferences ?: "",
    type = "Cat"
)

fun Pet.toEntity(
    existingLocalId: Int? = null,
    firebaseId: String? = null,
    synced: Boolean = false
) = PetEntity(
    localId = existingLocalId ?: localId,
    firebaseId = firebaseId,
    name = name,
    species = species,
    age = age,
    vaccinationInfo = vaccinationInfo,
    foodPreferences = foodPreferences,
    synced = synced
)
