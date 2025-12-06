package uk.ac.tees.mad.petcare.presentation.ui.screen.qr

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScannerOverlay() {

    val infinite = rememberInfiniteTransition()

    val lineOffset by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val boxWidth = size.width * 0.75f
            val boxHeight = size.width * 0.75f
            val left = (size.width - boxWidth) / 2f
            val top = (size.height - boxHeight) / 2f
            val right = left + boxWidth
            val bottom = top + boxHeight

            // Outer scan frame
            drawRect(
                color = Color.White.copy(alpha = 0.5f),
                topLeft = Offset(left, top),
                size = androidx.compose.ui.geometry.Size(boxWidth, boxHeight),
                style = Stroke(
                    width = 6f,
                    pathEffect = PathEffect.cornerPathEffect(45f)
                )
            )

            // Red scanning laser line
            val y = top + (lineOffset * boxHeight)

            drawLine(
                color = Color.Red.copy(alpha = 0.8f),
                start = Offset(left, y),
                end = Offset(right, y),
                strokeWidth = 4f
            )
        }
    }
}
