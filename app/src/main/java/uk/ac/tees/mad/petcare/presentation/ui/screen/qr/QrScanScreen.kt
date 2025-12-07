package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.petcare.utils.QrParser

@Composable
fun QRScanScreen(
    onBack: () -> Unit = {},
    onScanCompleted: (String, String) -> Unit // vaccine, date
) {
    var scannedText by remember { mutableStateOf("") }
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
                    val parsed = QrParser.parseVaccinationData(result)

                    if (parsed != null) {
                        val (vaccine, date) = parsed
                        onScanCompleted(vaccine, date)
                    }                }
            )
        ScannerOverlay()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        Surface(
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
//                    .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.7f))
            ) {
                Text(
                    text = if (scannedText.isEmpty()) "Scan a QR Code…" else "Scanned: $scannedText",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            }
        }
    }

