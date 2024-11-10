package com.example.dbapp.ui.client

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Customer
import com.example.dbapp.viewmodel.ContactViewModel
import java.util.Date

@Composable
fun EditContactFormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    customerId: Long,
    viewModel: ContactViewModel
) {

    // Ejecutamos la carga de datos cuando cambia el customerId
    LaunchedEffect(customerId) {
        viewModel.getCustomerById(customerId)  // Llamar para cargar el cliente cuando cambia el ID
    }
    // Recoger el estado del customer del ViewModel
    val customer by viewModel.customer.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Verificamos si estamos cargando. Si es así, mostramos un indicador de carga.
    if (isLoading) {
        // Indicador de carga mientras se recupera la data
        CircularProgressIndicator(Modifier.fillMaxSize())
    } else {
        // Estado para los campos del formulario
        var name by remember { mutableStateOf(customer.name ?: "") }
        var lastName by remember { mutableStateOf(customer.lastName ?: "") }
        var email by remember { mutableStateOf(customer.email ?: "") }
        var phone by remember { mutableStateOf(customer.phone ?: "") }
        var showError by remember { mutableStateOf(false) }



        // Una vez los datos estén cargados, evitamos redibujos innecesarios
        if (customer.id != 0L) {  // Asegurarnos de que el cliente existe antes de rellenar el formulario
            // Contenedor principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Barra superior con botón de guardar
                TopBarComponent(
                    navController = navController,
                    onSave = {
                        if (name.isNotEmpty()) {
                            // Crear un nuevo objeto Customer
                            val updatedCustomer = Customer(
                                name,
                                lastName,
                                email,
                                phone,
                                Date()
                            )
                            viewModel.updateCustomer(customerId, updatedCustomer) // Llamar a la función de actualización

                            // Limpiar los campos después de guardar
                            name = ""
                            lastName = ""
                            email = ""
                            phone = ""
                            showError = false

                            // Navegar hacia atrás
                            navController.navigateUp()
                        } else {
                            showError = true // Mostrar error si el nombre está vacío
                        }
                    },
                    modifier = Modifier.weight(0.1f)
                )

                // Títulos
                Text(
                    text = "Editar cliente",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Text(
                    text = "Información del contacto",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Componente del formulario de contacto
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
    }
}


@Preview(showBackground = true)
@Composable
fun EditContactFormScreenE() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp) // o el valor que necesites
    val viewModel = ContactViewModel()
    // Crear un cliente falso para la vista previa
    val fakeCustomer = Customer(
        "Juan",
        "Pérez",
        "juan.perez@example.com",
        "123456789",
        Date()
    )

    // Llamar a la función con el cliente falso
    EditContactFormScreen(navController = navController, innerPadding = innerPadding, customerId = 1, viewModel = viewModel)
}

