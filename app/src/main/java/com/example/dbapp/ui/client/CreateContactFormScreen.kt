package com.example.dbapp.ui.client

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Customer
import com.example.dbapp.ui.client.component.ContactForm
import com.example.dbapp.ui.client.component.TopBarClientComponent
import java.util.Date

@Composable
fun CreateContactFormScreen(
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
        TopBarClientComponent(
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

                    name = ""
                    lastName = ""
                    email = ""
                    phone = ""
                    showError = false

                    navController.navigateUp()
                } else {
                    showError = true
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
            isCreateForm = true,
            modifier = Modifier.weight(0.9f)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ContactFormScreenPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    CreateContactFormScreen(navController = navController, innerPadding = innerPadding) { contact ->

    }
}
