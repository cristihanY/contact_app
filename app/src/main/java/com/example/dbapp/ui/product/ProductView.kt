package com.example.dbapp.ui.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.dbapp.model.entity.Product
import com.example.dbapp.viewmodel.MessageServiceViewModel

@Composable
fun ProductView(
    navController: NavController,
    productViewModel: ProductViewModel,
    innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        val products by productViewModel.products.collectAsState()
        TopBarComponent(modifier = Modifier.weight(0.1f), navController = navController)

        CategoryChipsComponent(categories = listOf("Electrónica", "Moda", "Hogar", "Libros", "Deportes"), modifier = Modifier.weight(0.08f))
        ProductListComponent(products = products, modifier = Modifier.weight(0.82f))
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
            text = "Productos",
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
                navController.navigate("createProduct")
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
fun CategoryChipsComponent(categories: List<String>, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
    ) {
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                Chip(category = category)
            }
        }
    }
}


@Composable
fun Chip(category: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = category,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}


@Composable
fun ProductListComponent(products: List<Product>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp) // Padding general
    ) {
        items(products) { product ->
            val navController = rememberNavController()
            ProductItemComponent(product, navController)
        }
    }
}


@Composable
fun ProductItemComponent(product: Product, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Padding entre los elementos
        elevation = CardDefaults.elevatedCardElevation(4.dp) // Usar CardDefaults.elevatedCardElevation
    ) {
        Row(
            modifier = Modifier.padding(12.dp), // Padding interno del card
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Espacio entre elementos
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically)
                ) {
                    // Placeholder for image
                    Text("Img", Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = product.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Price: ${product.price}", fontSize = 14.sp)
                    Text(text = "Quantity: ${product.quantity}", fontSize = 14.sp)
                }
            }
            IconButton(
                onClick = {
                    //navController.navigate("productDetails/${product.id}")
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
    }
}



@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    val messageService = MessageServiceViewModel()// o el valor que necesites
    val productViewModel = ProductViewModel(messageService)
    ProductView(navController = navController, productViewModel = productViewModel, innerPadding = innerPadding)
}