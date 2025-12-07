package uk.ac.tees.mad.petcare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.mad.petcare.presentation.ui.screen.auth.LoginScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.auth.SignupScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.profile.PetProfileScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.profile.UserProfileScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.qr.QRScanScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.splash.SplashScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.tips.TipsScreen

object Routes {
    const val SIGNUP = "signup"
    const val LOGIN = "login"
    const val SPLASH = "splash"
    const val PET_PROFILE = "pet_profile"
    const val TIPS = "tips"
    const val QR_SCAN = "qr_scan"
    const val USER_PROFILE = "user_profile"
    const val PET_PROFILE_WITH_DATA = "pet_profile?vaccine={vaccine}&date={date}"
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
                onAddPet = { TODO() },
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.TIPS) {
            TipsScreen()
        }

        composable(Routes.QR_SCAN) {
            QRScanScreen(
                onScanCompleted = { vaccine, date ->
                    navController.navigate(
                        "pet_profile?vaccine=${vaccine}&date=${date}"
                    )
                },
                onBack = { navController.navigateUp() }
            )

        }

        composable(Routes.USER_PROFILE) {
            UserProfileScreen(
                onBack = { navController.navigateUp() },
                onLogout = { navController.navigate(Routes.LOGIN) { popUpTo(0) } })
        }

        composable(
            route = Routes.PET_PROFILE_WITH_DATA,
            arguments = listOf(
                navArgument("vaccine") { type = NavType.StringType; nullable = true },
                navArgument("date") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->

            val vaccine = backStackEntry.arguments?.getString("vaccine")
            val date = backStackEntry.arguments?.getString("date")

            PetProfileScreen(
                scannedVaccination = vaccine,
                scannedDate = date,
                onAddPet = {},
                onBackClick = { navController.navigateUp() }
            )
        }

    }
}

