package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
    onEditVaccination: (String, String) -> Unit // vaccine, date
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


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
        ) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onScanResult = { result ->
                    val parsed = QrParser.parseVaccinationData(result)
                    if (parsed != null) {
                        onEditVaccination(parsed.first, parsed.second)
                    } else {
                        scannedText = result
                    }
                }
            )
        }
        ScannerOverlay()
        ScanStatus(scannedText)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack,
                modifier = Modifier
//                    .align(Alignment.BottomStart)
                    .padding(16.dp)) {
                Text("Back")
            }
        }
    }
}

@Composable
fun ScanStatus(result: String) {
    Surface(
        tonalElevation = 3.dp,
        modifier = Modifier
//            .align(Alignment.Bottom)
            .padding(bottom = 100.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
//                .background(Color.White.copy(alpha = 0.7f))
        ) {
            Text(
                text = if (result.isEmpty()) "Scanning…" else "Scanned: $result",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

