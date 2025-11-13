package uk.ac.tees.mad.petcare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.education.name.presentation.ui.screen.auth.LoginScreen
import com.education.name.presentation.ui.screen.auth.SignupScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.profile.ProfileScreen
import uk.ac.tees.mad.petcare.presentation.ui.screen.splash.SplashScreen

object Routes {
    const val SIGNUP = "signup"
    const val LOGIN = "login"
    const val SPLASH = "splash"
    const val PROFILE = "profile"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onGoToLogin = { navController.navigate(Routes.LOGIN) },
                onGoToPetProfile = { navController.navigate(Routes.PROFILE) { popUpTo(0) } }
            )
        }

        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                })
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToSignup = {  navController.navigate(Routes.SIGNUP) }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen()
        }
    }
}
