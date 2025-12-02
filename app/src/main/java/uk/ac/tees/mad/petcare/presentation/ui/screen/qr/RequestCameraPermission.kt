package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestCameraPermission(
    onGranted: () -> Unit
) {
    val context = LocalContext.current

    var granted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher MUST be declared at composable level
    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            granted = result
        }

    // When granted → call callback once
    LaunchedEffect(granted) {
        if (granted) onGranted()
    }

    if (!granted) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Camera Permission Required") },
            text = { Text("We need camera access to scan vaccination QR codes.") },
            confirmButton = {
                Button(
                    onClick = {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                ) {
                    Text("Allow")
                }
            }
        )
    }
}
