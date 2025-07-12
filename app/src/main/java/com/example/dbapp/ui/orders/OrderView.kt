package com.example.dbapp.ui.orders


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import java.math.BigDecimal
import java.util.Date

@Composable
fun OrderView(
    navController: NavController,
    viewModel: OrderViewModel,
    innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        val products by viewModel.products.collectAsState()
        TopBarComponent(
            modifier = Modifier.weight(0.1f),
            navController = navController)

        FilterChipsComponent(categories = listOf("Sucursal de venta: Sucursal de la tienda"), modifier = Modifier.weight(0.08f))
        ProductListComponent(products = products, navController = navController, modifier = Modifier.weight(0.82f))
    }
}

@Composable
fun TopBarComponent(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Ordenes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.7f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { navController.navigate("search") },
            modifier = Modifier
                .weight(0.15f)
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.White
            )
        }
    }
}


@Composable
fun FilterChipsComponent(categories: List<String>, modifier: Modifier = Modifier) {
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
                ChipWithIcons(category = category)
            }
        }
    }
}

@Composable
fun ChipWithIcons(category: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.12f))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)) // Borde de color
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Fecha",
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Flecha hacia abajo",
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProductListComponent(products: List<Product>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        items(products) { product ->
            OrderItemComponent(product, navController)
        }
    }
}


@Composable
fun OrderItemComponent(product: Product, navController: NavController) {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Column {
                Text(text = "#1006 ${product.id}", fontWeight = FontWeight.Bold)
                Text(text = "Ayer a las 5:08 pm", fontSize = 14.sp)

                // Estados en chips
                Row(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusChip("Pagado", Color(0xFF4CAF50))       // Verde
                    StatusChip("Preparado", Color(0xFF2196F3))    // Azul
                }
            }
        }
        Text(text = "PEN 34.20", fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun StatusChip(text: String, color: Color) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .background(color, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 1.dp)
            .heightIn(min = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}



fun getStatusColor(status: String): Color = when (status) {
    "Pagado" -> Color(0xFF4CAF50)
    "Preparado" -> Color(0xFF2196F3)
    "Cancelado" -> Color(0xFFF44336)
    else -> Color.Gray
}

val product = Product().apply {
    id = 1L
    categoryId = 2L
    name = "Café Americano"
    price = BigDecimal("34.20")
    quantity = 10
    img = "https://example.com/images/cafe.jpg"
    barcode = "1234567890123"
    costPrice = BigDecimal("20.00")
    description = "Un delicioso café americano, recién hecho."
    createdAt = Date()
}

@Preview(showBackground = true)
@Composable
fun OrderItemComponentPreview() {
    val fakeNavController = rememberNavController()
    OrderItemComponent(product = product, navController = fakeNavController)
}
