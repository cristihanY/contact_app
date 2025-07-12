package com.example.dbapp.ui.client.component

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TopBarCartComponent(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                modifier = Modifier.size(28.dp)
            )
        }

    }
    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),)
}

@Composable
fun TopBarClientComponent(
    navController: NavController,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    textOnClick: String = "Guardar",
    icon: ImageVector = Icons.Default.Close
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = icon,
                contentDescription = "Cerrar",
                modifier = Modifier.size(28.dp)
            )
        }

        TextButton(
            onClick = {
                onSave()
            }
        ) {
            Text(
                text = textOnClick,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }
    }
    HorizontalDivider(
        modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),)
}

@Composable
fun TopBarDinamicComponent(
    navController: NavController,
    onSave: () -> Unit,
    showTitle: State<Boolean>,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        modifier = Modifier.size(28.dp)
                    )
                }

                if (!showTitle.value) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }

            TextButton(
                onClick = { onSave() },
                modifier = Modifier
            ) {
                Text(
                    text = "Guardar",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}



@Composable
fun TitleForm(titlePrimary: String, titleSecondary: String) {
    Text(
        text = titlePrimary,
        style = MaterialTheme.typography.labelLarge.copy(fontSize = 22.sp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )

    Text(
        text = titleSecondary,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp, color = Color.Gray),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}