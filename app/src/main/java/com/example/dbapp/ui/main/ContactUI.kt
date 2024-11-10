package com.example.dbapp.ui.main


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dbapp.model.entity.Customer
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun AddContactForm(onAddContact: (Customer) -> Unit) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                    val newCustomer = Customer(
                        name,
                        lastName,
                        email,
                        phone,
                        Date()
                    )
                    onAddContact(newCustomer)

                    // Clear fields
                    name = ""
                    lastName = ""
                    email = ""
                    phone = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Contact")
        }
    }
}

@Composable
fun ContactList(
    customers: List<Customer>,
    onEditContact: (Customer) -> Unit,
    onDeleteContact: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(customers) { contact ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Column for contact details
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = contact.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Tel: ${contact.phone}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = formatDate(contact.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    // Row for edit and delete icons
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = { onEditContact(contact) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { onDeleteContact(contact.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }

                // Divider to separate items
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun EditContactDialog(
    customer: Customer,
    onDismiss: () -> Unit,
    onSave: (Customer) -> Unit
) {
    var newName by remember { mutableStateOf(customer.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Contact") },
        text = {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Contact Name") }
            )
        },
        confirmButton = {
            Button(onClick = {
                // Crea un nuevo objeto Customer con el nombre actualizado
                val updatedCustomer = Customer(
                    newName,
                    customer.lastName,
                    customer.email,
                    customer.phone,
                    customer.date
                )
                onSave(updatedCustomer)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}
