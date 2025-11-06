package uk.ac.tees.mad.petcare.domain.model

data class Pet(
    val name: String = "",
    val species: String = "",
    val age: Int = 0,
    val vaccinationInfo: String = "",
    val foodPreferences: String = ""
)