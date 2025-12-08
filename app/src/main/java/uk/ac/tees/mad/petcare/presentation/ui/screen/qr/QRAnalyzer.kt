import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QRAnalyzer(
    private val onResult: (String) -> Unit,
    private val onScanning: (Boolean) -> Unit = {}
) : ImageAnalysis.Analyzer {
    private var isProcessed = false

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        if (isProcessed) {
            imageProxy.close()
            return
        }

        onScanning(true)   // alert UI we are actively scanning

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val rawValue = barcode.rawValue
                    if (!rawValue.isNullOrBlank()) {
                        isProcessed = true
                        onScanning(false)  // stop animation if needed
                        onResult(rawValue)
                        break
                    }
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }
}
