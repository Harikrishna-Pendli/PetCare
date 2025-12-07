package uk.ac.tees.mad.petcare.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.petcare.domain.model.Pet

@Composable
fun PetDialog(
    title: String,
    initialPet: Pet? = null,
    initialVaccination: String? = null,
    initialDate: String? = null,
    onDismiss: () -> Unit,
    onSave: (Pet) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var vaccination by remember {
        mutableStateOf(initialPet?.vaccinationInfo ?: initialVaccination ?: "")
    }
    var food by remember { mutableStateOf(initialPet?.foodPreferences ?: "") }

    val dateDisplay = initialDate ?: ""

    LaunchedEffect(initialPet) {
        initialPet?.let {
            name = it.name
            species = it.species
            age = it.age.toString()
            vaccination = it.vaccinationInfo
            food = it.foodPreferences
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
//                    if (name.isNotBlank()) {
                        onSave(
                            Pet(
                                localId = initialPet?.localId ?: 0,
                                firebaseId = initialPet?.firebaseId,
                                name = name.trim(),
                                species = species.trim(),
                                age = age.toIntOrNull() ?: 0,
                                vaccinationInfo = vaccination.trim(),
                                foodPreferences = food.trim(),
                                type = species.trim()
                            )
                        )
                        onDismiss()
//                    }
                }
            ) { Text("Save") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Pet Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Species") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = vaccination,
                    onValueChange = { vaccination = it },
                    label = { Text("Vaccination Info") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (dateDisplay.isNotBlank()) {
                    Text(
                        text = "Vaccination Date: $dateDisplay",
                    )
                }

                TextField(
                    value = food,
                    onValueChange = { food = it },
                    label = { Text("Food Preferences") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}