package uk.ac.tees.mad.petcare.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String="",
    val email: String= "",
    val notifications: Boolean = false
)
