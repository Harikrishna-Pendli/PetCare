package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditVaccinationScreen(
    vaccine: String,
    date: String,
    onCancel: () -> Unit,
    onProceed: (String, String) -> Unit
) {
    var vaccineText by remember { mutableStateOf(vaccine) }
    var dateText by remember { mutableStateOf(date) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Edit Vaccination Details") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = vaccineText,
                    onValueChange = { vaccineText = it },
                    label = { Text("Vaccine Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dateText,
                    onValueChange = { dateText = it },
                    label = { Text("Due Date") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onProceed(vaccineText.trim(), dateText.trim()) }) {
                Text("Continue")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}
