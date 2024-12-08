package com.example.dbapp.ui.uiutil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.dbapp.model.enums.MessageType
import com.example.dbapp.viewmodel.MessageServiceViewModel

@Composable
fun MessageSnackbar(
    viewModel: MessageServiceViewModel
) {
    val messages by viewModel.messageQueue.collectAsState()

    if (messages.isNotEmpty()) {
        val (message, type) = messages.first()

        val backgroundColor = when (type) {
            MessageType.SUCCESS -> Color(0xF248E943).copy(alpha = 0.98f)
            MessageType.WARNING -> Color(0xFFFFC107).copy(alpha = 0.9f)
            MessageType.ERROR -> Color(0xFFF44336).copy(alpha = 0.9f)
        }

        val borderColor = when (type) {
            MessageType.SUCCESS -> Color(0xFF20E629)
            MessageType.WARNING -> Color(0xFFFFB300)
            MessageType.ERROR -> Color(0xFFD32F2F)
        }


        val contentColor = when (type) {
            MessageType.SUCCESS -> Color.White
            MessageType.WARNING -> Color.White
            MessageType.ERROR -> Color.White
        }

        val icon = when (type) {
            MessageType.SUCCESS -> Icons.Default.CheckCircle
            MessageType.WARNING -> Icons.Default.Info
            MessageType.ERROR -> Icons.Default.Close
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .zIndex(100f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(2.dp, borderColor, shape = MaterialTheme.shapes.medium)
                    .background(backgroundColor, shape = MaterialTheme.shapes.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Message Icon",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                    tint = contentColor
                )

                Text(
                    text = message,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    color = contentColor
                )

                TextButton(
                    onClick = { viewModel.dismissMessage() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("X", color = contentColor)
                }
            }

            LaunchedEffect(message) {
                kotlinx.coroutines.delay(3000L)
                viewModel.dismissMessage()
            }
        }
    }
}



