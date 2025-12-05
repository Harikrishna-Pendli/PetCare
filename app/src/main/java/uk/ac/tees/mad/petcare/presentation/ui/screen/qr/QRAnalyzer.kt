package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QRAnalyzer(
    private val onResult: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private var isProcessed = false   // prevents duplicate scans

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        if (isProcessed) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        if (!rawValue.isNullOrBlank()) {
                            isProcessed = true
                            onResult(rawValue)
                            break
                        }
                    }
                }
                .addOnFailureListener {
                    // ignore errors, just continue scanning
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
