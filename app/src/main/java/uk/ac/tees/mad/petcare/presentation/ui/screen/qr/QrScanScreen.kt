package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QRScanScreen(
    onBack: () -> Unit = {},
    onScanCompleted: (String) -> Unit = {}
) {
    RequestCameraPermission {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            CameraPreview(
                modifier = Modifier.weight(1f)
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
}
