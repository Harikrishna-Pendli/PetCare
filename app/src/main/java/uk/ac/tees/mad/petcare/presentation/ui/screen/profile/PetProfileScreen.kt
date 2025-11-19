package uk.ac.tees.mad.petcare.presentation.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.presentation.ui.components.PetCard
import uk.ac.tees.mad.petcare.presentation.ui.components.PetDialog
import uk.ac.tees.mad.petcare.presentation.viewmodel.PetViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetProfileScreen(
    viewModel: PetViewModel = hiltViewModel(),
    onAddPetClick: () -> Unit = {},
    onEditPetClick: (Pet) -> Unit = {}
) {
    val pets by viewModel.pets.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var petToEdit by remember { mutableStateOf<Pet?>(null) }

    LaunchedEffect(true) {
        viewModel.loadPets()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Pet")
            }
        }
    ) { innerPadding ->
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
                                Text("Delete", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
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
}