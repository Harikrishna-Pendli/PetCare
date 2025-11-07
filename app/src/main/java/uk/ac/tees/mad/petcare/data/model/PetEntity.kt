package uk.ac.tees.mad.petcare.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),
    val firebaseId: String? = null,
    val name: String,
    val species: String,
    val age: Int,
    val vaccinationInfo: String?,
    val foodPreferences: String?,
    val synced: Boolean = false,
    val markedForDeletion: Boolean = false
)

fun PetEntity.toDomainModel() = uk.ac.tees.mad.petcare.domain.model.Pet(
    name = name,
    species = species,
    age = age,
    vaccinationInfo = vaccinationInfo.toString(),
    foodPreferences = foodPreferences.toString()
)


