package uk.ac.tees.mad.petcare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.education.name.presentation.ui.screen.auth.LoginScreen
import com.education.name.presentation.ui.screen.auth.SignupScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.profile.PetProfileScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.splash.SplashScreen

object Routes {
    const val SIGNUP = "signup"
    const val LOGIN = "login"
    const val SPLASH = "splash"
    const val PET_PROFILE = "pet_profile"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.SPLASH, modifier = modifier) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onGoToLogin = { navController.navigate(Routes.LOGIN) },
                onGoToPetProfile = { navController.navigate(Routes.PET_PROFILE) { popUpTo(0) } }
            )
        }

        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.PET_PROFILE) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                })
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.PET_PROFILE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToSignup = { navController.navigate(Routes.SIGNUP) }
            )
        }

        composable(Routes.PET_PROFILE) {
            PetProfileScreen(
                onAddPetClick = { TODO() },
                onEditPetClick = { TODO() }
            )
        }
    }
}
