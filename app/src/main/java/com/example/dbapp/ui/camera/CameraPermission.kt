package com.example.dbapp.ui.camera

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(onPermissionGranted: @Composable () -> Unit) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    Box(
        modifier = Modifier
            .fillMaxSize(), // Ocupa toda la pantalla
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        when (cameraPermissionState.status) {
            // Si el permiso ya ha sido concedido
            PermissionStatus.Granted -> {
                onPermissionGranted() // Llama a la función si ya tiene permiso
            }
            // Si el permiso aún no ha sido concedido
            is PermissionStatus.Denied -> {
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Solicitar permiso de cámara")
                }
            }
        }
    }
}

