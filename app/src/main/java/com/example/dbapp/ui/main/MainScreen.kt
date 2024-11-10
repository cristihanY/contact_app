package com.example.dbapp.ui.main

import androidx.compose.foundation.layout.Column

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.dbapp.model.entity.Customer
import com.example.dbapp.viewmodel.ContactViewModel

@Composable
fun MainScreen(
    navController: NavController,
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier
) {
    val contacts by contactViewModel.customers.collectAsState()

    var editingCustomer by remember { mutableStateOf<Customer?>(null) }

    Column(modifier = modifier) {
        AddContactForm(onAddContact = { name -> contactViewModel.addCustomer(name) })
        ContactList(
            customers = contacts,
            onEditContact = { contact ->
                editingCustomer = contact // Abre el diálogo de edición
            },
            onDeleteContact = { id -> contactViewModel.deleteCustomer(id) } // Usa el ViewModel
        )
    }

    editingCustomer?.let { contact ->
        EditContactDialog(
            customer = contact,
            onDismiss = { editingCustomer = null },
            onSave = { customer ->
                contactViewModel.updateCustomer(contact.id, customer) // Usa el ViewModel
                editingCustomer = null
            }
        )
    }
}




