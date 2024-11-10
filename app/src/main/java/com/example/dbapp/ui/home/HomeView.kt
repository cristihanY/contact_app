package com.example.dbapp.ui.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


@Composable
fun HomeView(navController: NavController, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // Componente superior (10%)
        TopBarComponent(navController = navController, modifier = Modifier.weight(0.1f))

        // Carrusel (80%)
        CarouselComponent(modifier = Modifier.weight(0.8f))

        // Componente inferior (10%)
        BottomBarComponent(modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun TopBarComponent(navController: NavController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Padding horizontal general
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los elementos
    ) {
        // Texto "My Store" (70%)
        Text(
            text = "My Store",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.7f),
            textAlign = TextAlign.Start
        )

        // Espacio entre el texto y los botones
        Spacer(modifier = Modifier.width(8.dp))

        // Botón circular con ícono de escritura (15%)
        IconButton(
            onClick = { navController.navigate("scanner") },
            modifier = Modifier
                .weight(0.15f)
                .size(40.dp) // Tamaño del botón circular
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp) // Padding interno para el icono
        ) {
            Icon(
                imageVector = Icons.Default.Create, // Ícono de escribir
                contentDescription = "Escribir",
                tint = Color.White
            )
        }

        // Espacio entre los botones
        Spacer(modifier = Modifier.width(8.dp))

        // Botón circular con ícono de búsqueda (15%)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Espacio para los indicadores
    ) {
        val listState: LazyListState = rememberLazyListState()
        val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        val currentPage by remember { derivedStateOf { listState.firstVisibleItemIndex } }

        Column {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                flingBehavior = snapFlingBehavior
            ) {
                items(pages) { productList ->
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth(1f)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            //Text("Bienvenido a la Pantalla de Inicio", fontSize = 24.sp)
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(productList) { item ->
                                    CardItem(item.title, item.icon)
                                }
                            }
                        }
                    }
                }
            }

            // Indicadores
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                pages.forEachIndexed { index, _ ->
                    val color = if (index == currentPage) MaterialTheme.colorScheme.primary else Color.Gray
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(8.dp)
                            .background(color, CircleShape)
                    )
                }
            }
        }
    }
}


@Composable
fun BottomBarComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /* Lógica para agregar al carrito */ },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Ir al carrito")
        }
    }
}

data class CardItemData(val title: String, val icon: ImageVector)

val pages = listOf(
    listOf(
        CardItemData("Agregar Venta Personalizada", Icons.Default.Add),
        CardItemData("Aplicar Descuento", Icons.Default.Person),
        CardItemData("Enviar", Icons.Default.Send),
        CardItemData("Guardar", Icons.Default.Delete),
        CardItemData("Ver", Icons.Default.Add)
    ),
    listOf(
        CardItemData("Producto A", Icons.Default.ShoppingCart),
        CardItemData("Producto B", Icons.Default.Favorite),
        CardItemData("Producto C", Icons.Default.Home),
        CardItemData("Producto D", Icons.Default.Info),
        CardItemData("Producto E", Icons.Default.Settings)
    ),
    // Añade más páginas con otros productos si es necesario
)


@Composable
fun CardItem(title: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(130.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(icon, contentDescription = title, modifier = Modifier.size(40.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp) // o el valor que necesites
    HomeView(navController = navController, innerPadding = innerPadding)
}
