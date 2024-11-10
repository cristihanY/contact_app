package com.example.dbapp.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Customer
import com.example.dbapp.viewmodel.ContactViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun ClientView(
    navController: NavController,
    contactViewModel: ContactViewModel,
    innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        val customers by contactViewModel.customers.collectAsState()
        TopBarComponent(modifier = Modifier.weight(0.1f), navController = navController)
        ContactListComponent(
            customers = customers,
            navController = navController,
            modifier = Modifier.weight(0.9f)
        )
    }

}
@Composable
fun TopBarComponent(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Padding horizontal general
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los elementos
    ) {
        // Texto "Productos" (70%)
        Text(
            text = "Clientes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.7f),
            textAlign = TextAlign.Start
        )
        // Espacio entre el texto y los botones
        Spacer(modifier = Modifier.width(8.dp))
        // Botón circular con ícono +
        IconButton(
            onClick = {
                navController.navigate("createContact")
            },
            modifier = Modifier
                .weight(0.15f)
                .size(40.dp) // Tamaño del botón circular
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp) // Padding interno para el icono
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Ícono +
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
        // Espacio entre los botones
        Spacer(modifier = Modifier.width(8.dp))
        // Botón circular con ícono de búsqueda
        IconButton(
            onClick = { /* Acción para buscar */ },
            modifier = Modifier
                .weight(0.15f)
                .size(40.dp) // Tamaño del botón circular
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp) // Padding interno para el icono
        ) {
            Icon(
                imageVector = Icons.Default.Search, // Ícono de buscar
                contentDescription = "Buscar",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ContactListComponent(customers: List<Customer>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp) // Padding horizontal
    ) {
        items(customers) { contact ->
            ContactItemComponent(contact, navController)
        }
    }
}

@Composable
fun ContactItemComponent(customer: Customer, navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Padding vertical entre los elementos
    ) {
        Row(
            modifier = Modifier.padding(12.dp), // Padding interno del card
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Espacio entre elementos
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = customer.name + " " + customer.lastName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = customer.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "+51 ${customer.phone}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = formatDate(customer.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            IconButton(
                onClick = {
                    // Navega a los detalles del contacto
                    //navController.navigate("contactDetails/${contact.id}")
                    println("Navigating to edit customer with ID: ${customer.id}") // Debería imprimir 22
                    navController.navigate("editCustomer/${customer.id}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight, // Ícono de flecha derecha
                    contentDescription = "Ver detalles",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp) // Línea divisoria debajo de cada contacto
    }
}

@Composable
fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(date)
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp) // o el valor que necesites
    val contactViewModel = ContactViewModel()
    ClientView(navController = navController, contactViewModel = contactViewModel,  innerPadding = innerPadding)
}