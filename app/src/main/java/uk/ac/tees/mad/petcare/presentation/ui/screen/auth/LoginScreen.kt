package uk.ac.tees.mad.petcare.presentation.ui.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.presentation.viewmodel.AuthViewModel
import uk.ac.tees.mad.petcare.util.UiState
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit, // navigate after successful login
    onNavigateToSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by viewModel.signInState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is UiState.Success) {
            onLoginSuccess()
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
                Text("Login to continue", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

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
                    onClick = { viewModel.signIn(email.trim(), password.trim()) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = authState !is UiState.Loading
                ) {
                    Text(
                        when (authState) {
                            is UiState.Loading -> "Logging In..."
                            else -> "Login"
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Don't have an account? ",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium

                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Create Account",
                        modifier = Modifier.clickable(onClick = onNavigateToSignup),
                        color = MaterialTheme.colorScheme.onBackground,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (authState is UiState.Error) {
                    Text(
                        text = (authState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        LaunchedEffect(authState) {
            if (authState is UiState.Success) {
                onLoginSuccess()
            }
        }
    }
}

@Composable
fun LoginScreenPreview() {

    var email by remember { mutableStateOf("user@mail.com") }
    var password by remember { mutableStateOf("password123") }

    // Fake authentication state for preview
    val authState = remember { mutableStateOf<UiState<Unit>>(UiState.Idle) }

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

                Text("Login to continue", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

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
                    onClick = { authState.value = UiState.Loading },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = authState.value !is UiState.Loading
                ) {
                    Text(
                        when (authState.value) {
                            is UiState.Loading -> "Logging In..."
                            else -> "Login"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Don't have an account? ",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Create Account",
                        modifier = Modifier.clickable { /* Preview only */ },
                        color = MaterialTheme.colorScheme.onBackground,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Mock error example
                if (authState.value is UiState.Error) {
                    Text(
                        text = (authState.value as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview_UI() {
    LoginScreenPreview()
}
