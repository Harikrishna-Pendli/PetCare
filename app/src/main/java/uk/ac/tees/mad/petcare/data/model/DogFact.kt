package uk.ac.tees.mad.petcare.data.model

data class DogFact(
    val id: Int,
    val user: String?,
    val text: String,
    val created_at: String
)
