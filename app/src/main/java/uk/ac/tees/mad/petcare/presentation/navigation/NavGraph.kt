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

object Routes {
    const val SIGNUP = "signup"
    const val LOGIN = "login"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context= LocalContext.current
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                   Toast.makeText(context,"SIGNED UP",Toast.LENGTH_LONG).show()
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    Toast.makeText(context,"SIGNED UP",Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}
