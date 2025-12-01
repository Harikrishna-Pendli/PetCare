package uk.ac.tees.mad.petcare.domain.model

data class Pet(
    val localId: Int = 0,
    val firebaseId: String? = null,
    val name: String = "",
    val species: String = "",
    val age: String = 0,
    val vaccinationInfo: String = "",
    val foodPreferences: String = "",
    val type: String
)