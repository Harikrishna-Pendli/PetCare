package uk.ac.tees.mad.petcare.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firebaseId: String? = null,
    val name: String,
    val species: String,
    val age: Int,
    val vaccinationInfo: String,
    val foodPreferences: String
)