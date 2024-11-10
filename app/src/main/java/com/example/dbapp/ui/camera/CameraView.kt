package com.example.dbapp.ui.camera

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanner



@Composable
fun CameraView(context: Context, cameraSelector: CameraSelector) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var barcodeResult by remember { mutableStateOf<String?>(null) }

    // Agregar DisposableEffect para liberar la cámara cuando se salga de la vista
    DisposableEffect(Unit) {
        onDispose {
            releaseCamera(context)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            update = { previewView ->
                startCamera(context, previewView, lifecycleOwner, cameraSelector) { barcode ->
                    barcodeResult = barcode.displayValue ?: "No se detectó el valor"
                }
            }
        )
        ScannerOverlay(modifier = Modifier.fillMaxSize())

        barcodeResult?.let {
            Text(
                text = it,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

// Función que inicia la cámara con el selector especificado
@OptIn(ExperimentalGetImage::class)
private fun startCamera(
    context: Context,
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: CameraSelector,
    onBarcodeDetected: (Barcode) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        val barcodeScannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_CODE_39,
                Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_PDF417,
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_DATA_MATRIX
            ).build()
        val barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions)

        val targetResolution = android.util.Size(1280, 720)
        val imageAnalyzer = ImageAnalysis.Builder()
            .setTargetResolution(targetResolution)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also { it.setAnalyzer(context.mainExecutor, createAnalyzer(barcodeScanner, onBarcodeDetected)) }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer)
        } catch (exc: Exception) {
            Log.e("CameraView", "Error al iniciar la cámara: ${exc.message}")
        }
    }, ContextCompat.getMainExecutor(context))
}

// Función que libera la cámara llamando a unbindAll
private fun releaseCamera(context: Context) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        cameraProvider.unbindAll() // Libera todos los recursos de la cámara
    }, ContextCompat.getMainExecutor(context))
}

@OptIn(ExperimentalGetImage::class)
private fun createAnalyzer(barcodeScanner: BarcodeScanner, onBarcodeDetected: (Barcode) -> Unit): ImageAnalysis.Analyzer {
    return ImageAnalysis.Analyzer { imageProxy ->
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.let { onBarcodeDetected(it) }
                }
                .addOnFailureListener { e ->
                    Log.e("CameraView", "Error en detección: ${e.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}


@Composable
fun ScannerOverlay(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val overlayColor = Color(0x80000000)
            val frameSize = size.width * 0.8f
            val frameTop = (size.height - frameSize) / 2
            val frameLeft = (size.width - frameSize) / 2

            // Desvanecimiento alrededor del área de interés
            drawRect(color = overlayColor, size = size)

            // Recuadro transparente con esquinas redondeadas para el área de interés
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(frameLeft, frameTop),
                size = Size(frameSize, frameSize),
                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()), // Esquinas redondeadas
                blendMode = BlendMode.Clear
            )

            // Estilo para las esquinas
            val cornerStyle = Stroke(width = 4.dp.toPx())
            val cornerRadius = 12.dp.toPx()
            val extensionLength = 50.dp.toPx()

            // Dibuja las esquinas del recuadro con líneas continuas
            drawPath(
                path = Path().apply {
                    moveTo(frameLeft, frameTop + cornerRadius + extensionLength)
                    arcTo(Rect(Offset(frameLeft, frameTop), Size(cornerRadius * 2, cornerRadius * 2)), 180f, 90f, false)
                    lineTo(frameLeft + cornerRadius + extensionLength, frameTop)

                    moveTo(frameLeft + frameSize - cornerRadius - extensionLength, frameTop)
                    arcTo(Rect(Offset(frameLeft + frameSize - 2 * cornerRadius, frameTop), Size(cornerRadius * 2, cornerRadius * 2)), 270f, 90f, false)
                    lineTo(frameLeft + frameSize, frameTop + cornerRadius + extensionLength)

                    moveTo(frameLeft + frameSize, frameTop + frameSize - cornerRadius - extensionLength)
                    arcTo(Rect(Offset(frameLeft + frameSize - 2 * cornerRadius, frameTop + frameSize - 2 * cornerRadius), Size(cornerRadius * 2, cornerRadius * 2)), 0f, 90f, false)
                    lineTo(frameLeft + frameSize - cornerRadius - extensionLength, frameTop + frameSize)

                    moveTo(frameLeft + cornerRadius + extensionLength, frameTop + frameSize)
                    arcTo(Rect(Offset(frameLeft, frameTop + frameSize - 2 * cornerRadius), Size(cornerRadius * 2, cornerRadius * 2)), 90f, 90f, false)
                    lineTo(frameLeft, frameTop + frameSize - cornerRadius - extensionLength)
                },
                color = Color.White,
                style = cornerStyle
            )
        }
    }
}
