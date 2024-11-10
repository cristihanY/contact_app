package com.example.dbapp.ui.client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Customer
import java.util.Date

@Composable
fun ContactFormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    onSave: (Customer) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        TopBarComponent(
            navController = navController,
            onSave = {
                if (name.isNotEmpty()) {
                    val newCustomer = Customer(
                        name,
                        lastName,
                        email,
                        phone,
                        Date()
                    )
                    onSave(newCustomer)

                    // Limpiar los campos después de guardar y resetear el estado de error
                    name = ""
                    lastName = ""
                    email = ""
                    phone = ""
                    showError = false

                    // Navega hacia atrás solo después de guardar exitosamente
                    navController.navigateUp()
                } else {
                    showError = true // Muestra el error si el nombre está vacío
                }
            },
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = "Agregar cliente",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        Text(
            text = "Información del contacto",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, color = Color.Gray),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ContactForm(
            name = name,
            onNameChange = { name = it },
            lastName = lastName,
            onLastNameChange = { lastName = it },
            email = email,
            onEmailChange = { email = it },
            phone = phone,
            onPhoneChange = { phone = it },
            showError = showError,
            modifier = Modifier.weight(0.9f)
        )
    }
}

@Composable
fun TopBarComponent(
    navController: NavController,
    onSave: () -> Unit, // Agregado el parámetro onSave
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                modifier = Modifier.size(24.dp)
            )
        }
        TextButton(
            onClick = {
                onSave() // Ejecuta la función onSave y solo cerrará si es válido
            }
        ) {
            Text(
                text = "Guardar",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ContactForm(
    name: String,
    onNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    showError: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterLastName = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterPhone = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequesterName.requestFocus() // Enfocar automáticamente el campo de nombre
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                onNameChange(it)
            },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterName),
            isError = showError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterLastName.requestFocus() }
            )
        )
        if (showError) {
            Text(
                text = "El nombre es obligatorio",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterLastName),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterEmail.requestFocus() }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Correo") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterEmail),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterPhone.requestFocus() }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = { Text("Teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterPhone),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactFormScreenPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp) // o el valor que necesites
    ContactFormScreen(navController = navController, innerPadding = innerPadding) { contact ->
        // Acciones al guardar el contacto
    }
}
