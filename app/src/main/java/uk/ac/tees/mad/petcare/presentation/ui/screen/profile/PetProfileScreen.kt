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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.presentation.ui.components.PetCard
import uk.ac.tees.mad.petcare.presentation.ui.components.PetDialog
import uk.ac.tees.mad.petcare.presentation.ui.components.ProfileHeader
import uk.ac.tees.mad.petcare.presentation.viewmodel.PetViewModel
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetProfileScreen(
    viewModel: PetViewModel = hiltViewModel(),
    onAddPet: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val pets by viewModel.pets.collectAsState()
    val openDialog by viewModel.openAddPetDialog.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var petToEdit by remember { mutableStateOf<Pet?>(null) }
    val context = LocalContext.current
//
//    LaunchedEffect(pets, openDialog, onAddPet) {
//
//        // Load pets only once
//        if (pets.isEmpty()) {
//            viewModel.loadPets()
//        }
//
//        // Trigger from ViewModel (FAB press)
//        if (openDialog) {
//            viewModel.consumeAddPetDialog()
//            petToEdit = null
//            showAddDialog = true
//        }
//
//        // Trigger from screen params (if needed)
//        if (onAddPet != {}) {
//            showAddDialog = true
//        }
//    }

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
//            onBackClick = onBackClick
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
            }
        )
    }
    if (petToEdit != null) {
        PetDialog(
            title = "Edit Pet",
            initialPet = petToEdit,
            onDismiss = { petToEdit = null },
            onSave = { updated ->
                viewModel.updatePet(
                    id = petToEdit?.localId,
                    pet = updated
                )
                petToEdit = null
            }
        )
    }
    LaunchedEffect(onAddPet) {
        if (onAddPet != {}) {
            showAddDialog = true
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetProfileScreenPreview() {

    val mockPets = listOf(
        Pet(
            localId = 1,
            name = "Buddy",
            species = "Dog",
            age = 3,
            vaccinationInfo = "Fully vaccinated",
            foodPreferences = "Chicken & Rice"
        ),
        Pet(
            localId = 2,
            name = "Luna",
            species = "Cat",
            age = 2,
            vaccinationInfo = "Needs booster",
            foodPreferences = "Tuna"
        )
    )

    var showAddDialog by remember { mutableStateOf(false) }
    var petToEdit by remember { mutableStateOf<Pet?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Pet")
            }
        }
    ) { innerPadding ->

        ProfileHeader("Pet Profile")

        Spacer(Modifier.height(20.dp))
        if (mockPets.isEmpty()) {
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
                items(mockPets, key = { it.localId }) { pet ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = { false } // no delete in preview
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
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
    }

    if (showAddDialog) {
        PetDialog(
            title = "Add Pet",
            onDismiss = { showAddDialog = false },
            onSave = { showAddDialog = false }
        )
    }

    if (petToEdit != null) {
        PetDialog(
            title = "Edit Pet",
            initialPet = petToEdit,
            onDismiss = { petToEdit = null },
            onSave = { petToEdit = null }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PetProfileScreenPreview_UI() {
    PetProfileScreenPreview()
}
