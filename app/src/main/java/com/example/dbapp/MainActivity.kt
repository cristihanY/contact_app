package com.example.dbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dbapp.core.AppDatabase
import com.example.dbapp.core.init.MyApp
import com.example.dbapp.model.entity.Contact
import com.example.dbapp.service.ContactService
import com.example.dbapp.ui.theme.DbAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {

    private var contactService: ContactService? = null
    private var contacts by mutableStateOf<List<Contact>>(emptyList())
    private var editingContact by mutableStateOf<Contact?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database: AppDatabase = MyApp.getInstance().database
        contactService = ContactService(database)

        CoroutineScope(Dispatchers.IO).launch {
            contacts = contactService!!.getAllContacts()
        }

        setContent {
            DbAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AddContactForm(onAddContact = { name ->
                            addContact(name)
                        })
                        ContactList(
                            contacts = contacts,
                            onEditContact = { contact ->
                                editingContact = contact
                            },
                            onDeleteContact = { id ->
                                deleteContact(id)
                            }
                        )
                    }
                    editingContact?.let { contact ->
                        EditContactDialog(
                            contact = contact,
                            onDismiss = { editingContact = null },
                            onSave = { name ->
                                updateContact(contact.id, name)
                                editingContact = null
                            }
                        )
                    }
                }
            }
        }
    }

    private fun addContact(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val newContact = Contact(name, "123456789", Date())
            contactService?.addContact(newContact)
            contacts = contactService?.getAllContacts() ?: emptyList()
        }
    }

    private fun updateContact(id: Int, name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            contactService?.updateContact(id, name)
            contacts = contactService?.getAllContacts() ?: emptyList()
        }
    }

    private fun deleteContact(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            contactService?.removeContact(id)
            contacts = contactService?.getAllContacts() ?: emptyList()
        }
    }

}


@Composable
fun AddContactForm(onAddContact: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Contact Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (name.isNotEmpty()) {
                    onAddContact(name)
                    name = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Contact")
        }
    }
}

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

@Composable
fun ContactList(
    contacts: List<Contact>,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(contacts) { contact ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
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
    contact: Contact,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var newName by remember { mutableStateOf(contact.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Contacto") },
        text = {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Nombre") }
            )
        },
        confirmButton = {
            Button(onClick = {
                onSave(newName)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DbAppTheme {
        Greeting("Android")
    }
}