package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QRScanScreen(
    onBack: () -> Unit = {},
    onScanCompleted: (String) -> Unit = {}
) {
    var scannedText by remember { mutableStateOf<String?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }


    RequestCameraPermission(
        onGranted = {
            permissionGranted = true
        }
    )

    if (!permissionGranted) {
        return
    }
    Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CameraPreview(
                modifier = Modifier.weight(1f),
                onScanResult = { result ->
                    scannedText = result
                    onScanCompleted(result)
                }
            )

            Surface(
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Align QR code inside the frame",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

