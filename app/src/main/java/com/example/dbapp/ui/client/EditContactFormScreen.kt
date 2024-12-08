package com.example.dbapp.ui.client

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Customer
import com.example.dbapp.ui.client.component.ContactForm
import com.example.dbapp.ui.client.component.TopBarClientComponent
import com.example.dbapp.ui.uiutil.DeleteConfirmationDialog
import com.example.dbapp.viewmodel.MessageServiceViewModel

@Composable
fun EditContactFormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    customerId: Long,
    viewModel: ContactViewModel
) {
    LaunchedEffect(customerId) {
        viewModel.getCustomerById(customerId)
    }

    val customer by viewModel.customer.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize())
    } else {
        var name by remember { mutableStateOf(customer.name ?: "") }
        var lastName by remember { mutableStateOf(customer.lastName ?: "") }
        var email by remember { mutableStateOf(customer.email ?: "") }
        var phone by remember { mutableStateOf(customer.phone ?: "") }
        var showError by remember { mutableStateOf(false) }

        if (customer.id != 0L) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TopBarClientComponent(
                    navController = navController,
                    onSave = {
                        if (name.isNotEmpty()) {
                            val updatedCustomer = Customer(
                                name,
                                lastName,
                                email,
                                phone,
                                Date()
                            )
                            viewModel.updateCustomer(customerId, updatedCustomer)
                            navController.navigateUp()
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.weight(0.1f)
                )

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
                    isCreateForm = false,
                    modifier = Modifier.weight(0.9f)
                )

                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text("Eliminar cliente", color = Color.White)
                }

                if (showDeleteDialog) {
                    DeleteConfirmationDialog(
                        onConfirm = {
                            viewModel.deleteCustomer(customerId)
                            showDeleteDialog = false
                            navController.navigateUp()
                        },
                        onDismiss = { showDeleteDialog = false }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditContactFormScreenE() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    val messageViewModel = MessageServiceViewModel()
    val viewModel = ContactViewModel(messageViewModel)

    EditContactFormScreen(navController = navController, innerPadding = innerPadding, customerId = 1, viewModel = viewModel)
}

