package uk.ac.tees.mad.petcare.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph
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
    val context= LocalContext.current
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onGoToLogin = { navController.navigate(Routes.LOGIN) { popUpTo(0) } },
                onGoToPetProfile = { navController.navigate(Routes.PROFILE) { popUpTo(0) } }
            )
        }

        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = { navController.navigate(Routes.PROFILE) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
//                    Toast.makeText(context,"SIGNED UP",Toast.LENGTH_LONG).show(),
                    { navController.navigate(Routes.PROFILE) }
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen()
        }
    }
}
