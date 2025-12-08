package uk.ac.tees.mad.petcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.petcare.presentation.navigation.BottomNavBar
import uk.ac.tees.mad.petcare.presentation.navigation.NavGraph
import uk.ac.tees.mad.petcare.presentation.navigation.Routes
import uk.ac.tees.mad.petcare.presentation.ui.theme.PetCareTheme
import uk.ac.tees.mad.petcare.presentation.viewmodel.PetViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val showBottomBar =
                currentRoute?.startsWith(Routes.PET_PROFILE) == true ||
                        currentRoute == Routes.TIPS ||
                        currentRoute == Routes.USER_PROFILE ||
                        currentRoute == Routes.QR_SCAN
            val petViewModel: PetViewModel = hiltViewModel()

            PetCareTheme {
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(navController)
                        }
                    },
//                    floatingActionButton = {
//                        if (currentRoute == "pet_profile") {
//                            FloatingActionButton(
//                                onClick = {
//                                    petViewModel.triggerAddPetDialog()
//                                },                                containerColor = MaterialTheme.colorScheme.surface,
//                                contentColor = MaterialTheme.colorScheme.onSurface,
//                                elevation = FloatingActionButtonDefaults.elevation(
//                                    defaultElevation = 8.dp,
//                                    pressedElevation = 12.dp
//                                )
//                            ) {
//                                Icon(Icons.Default.Add, contentDescription = "Add Pet")
//                            }
//                        }
//                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
