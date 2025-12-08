package uk.ac.tees.mad.petcare.presentation.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.presentation.ui.components.PetCard
import uk.ac.tees.mad.petcare.presentation.ui.components.PetDialog
import uk.ac.tees.mad.petcare.presentation.ui.components.ProfileHeader
import uk.ac.tees.mad.petcare.presentation.ui.screen.qr.EditVaccinationScreen
import uk.ac.tees.mad.petcare.presentation.viewmodel.PetViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetProfileScreen(
    viewModel: PetViewModel = hiltViewModel(),
    onAddPet: () -> Unit = {},
    scannedVaccination: String? = null,
    scannedDate: String? = null,
    onBackClick: () -> Unit = {}
) {
    val pets by viewModel.pets.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var petToEdit by remember { mutableStateOf<Pet?>(null) }

    var editVaccine by remember { mutableStateOf<String?>(null) }
    var editDate by remember { mutableStateOf<String?>(null) }
    var pendingVaccination by remember { mutableStateOf<String?>(null) }
    var pendingDate by remember { mutableStateOf<String?>(null) }


    // If scanned data is passed from QRScanScreen, store it
    LaunchedEffect(scannedVaccination, scannedDate) {
        if (!scannedVaccination.isNullOrBlank() && !scannedDate.isNullOrBlank()) {
            editVaccine = scannedVaccination
            editDate = scannedDate
        }
    }

    if (editVaccine != null && editDate != null) {
        EditVaccinationScreen(
            vaccine = editVaccine!!,
            date = editDate!!,
            onCancel = {
                editVaccine = null
                editDate = null
            },
            onProceed = { v, d ->
                // proceed to show Add Pet dialog pre-filled
                editVaccine = null
                editDate = null
                pendingVaccination = v
                pendingDate = d
                showAddDialog = true
            }
        )
    }

    if (showAddDialog && pendingVaccination != null) {
        PetDialog(
            title = "Add Pet (Scanned)",
            initialVaccination = pendingVaccination,
            initialDate = pendingDate,
            onDismiss = {
                showAddDialog = false
                pendingVaccination = null
                pendingDate = null
            },
            onSave = { pet ->
                viewModel.addPet(pet)
                showAddDialog = false
                pendingVaccination = null
                pendingDate = null
            }
        )
    }

    LaunchedEffect(true) {
        viewModel.loadPets()
    }

//    LaunchedEffect(openDialog) {
//        if (openDialog) {
//            viewModel.consumeAddPetDialog()
//            petToEdit = null
//            showAddDialog = true
//        }
//    }


//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp)
//    ) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Pet")
            }
        }
    ) { innerPadding ->
        ProfileHeader(
            "Pet Profile",
        )
        if (pets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No pets added yet 🐾")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp)
            ) {
                items(pets, key = { it.localId }) { pet ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = { dismissValue ->
                            if (dismissValue == DismissValue.DismissedToEnd ||
                                dismissValue == DismissValue.DismissedToStart
                            ) {
                                viewModel.deletePet(pet.localId)
                                true
                            } else false
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text("Delete", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        dismissContent = {
                            PetCard(
                                pet = pet,
                                onClick = { petToEdit = pet }
                            )
                        }
                    )
                }
            }
        }
//        Test notifications
//        Button(onClick = {
//            NotificationHelper.showNotification(
//                context,
//                id = 1,
//                title = "Test Notification",
//                body = "Notifications are working!"
//            )
//        }) {
//            Text("Show Test Notification")
//        }


    }
    if (showAddDialog) {
        PetDialog(
            title = "Add Pet",
            onDismiss = { showAddDialog = false },
            onSave = { pet ->
                viewModel.addPet(pet)
                showAddDialog = false
            }
        )
    }
//    if (petToEdit != null) {
//        PetDialog(
//            title = "Edit Pet",
//            initialPet = petToEdit,
//            onDismiss = { petToEdit = null },
//            onSave = { updatedPet ->
//                viewModel.updatePet(petToEdit?.localId, updatedPet)
//                petToEdit = null
////                updated ->
////                viewModel.updatePet(
////                    id = petToEdit?.localId,
////                    pet = updated
////                )
////                petToEdit = null
//            }
//        )
//    }
    if (petToEdit != null) {
        PetDialog(
            title = "Edit Pet",
            initialPet = petToEdit,
            onDismiss = { petToEdit = null },
            onSave = { updatedPet ->
                viewModel.updatePet(petToEdit?.localId, updatedPet)
                petToEdit = null
            }
        )
    }
}


@Preview(showBackground = true, name = "PetCare – Pet Profile")
@Composable
fun PetCarePetProfileExactPreview() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Add Pet")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            // Profile Header
            Text(
                text = "Pet Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))

            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No pets added yet 🐾")
            }

            // FAB is already shown
        }
    }
}
