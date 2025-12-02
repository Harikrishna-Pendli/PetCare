package uk.ac.tees.mad.petcare.presentation.ui.screen.auth

//import com.education.name.presentation.ui.viewmodel.UserViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.presentation.viewmodel.AuthViewModel
import uk.ac.tees.mad.petcare.util.UiState

@Composable
fun SignupScreen(
    viewModel: AuthViewModel = hiltViewModel(),
//    userViewModel: UserViewModel=hiltViewModel(),
    onSignupSuccess: () -> Unit = {} // navigate after successful signup
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val authState by viewModel.signUpState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is UiState.Success) {
            val user = User(name = name, email = email)
//            userViewModel.createUser(user) // Fire-and-forget
            onSignupSuccess()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text("Sign up to continue", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.signUp(email.trim(), password.trim()) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = authState !is UiState.Loading
                ) {
                    Text(
                        when (authState) {
                            is UiState.Loading -> "Signing Up..."
                            else -> "Sign Up"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Show error message
                if (authState is UiState.Error) {
                    Text(
                        text = (authState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignupScreen() {
    MaterialTheme {
        SignupScreen(
//            viewModel = FakeAuthViewModel(),
            onSignupSuccess = {}
        )
    }
}
