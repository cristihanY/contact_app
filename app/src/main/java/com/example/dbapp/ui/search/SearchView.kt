package com.example.dbapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchView(
    navController: NavController,
    innerPadding: PaddingValues
) {
    // Estado local para la consulta de búsqueda
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // Barra de búsqueda
        SearchBarComponent(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp), // Altura fija para la barra de búsqueda
            searchQuery = searchQuery,
            onSearchQueryChange = { newQuery ->
                searchQuery = newQuery
            }
        )

        CategoryChipsComponent(
            categories = listOf("Productos", "Clientes", "Pedidos"),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
        ) { category ->
            // Aquí puedes agregar lógica adicional si necesitas realizar acciones al seleccionar un chip
        }

        SearchHeader(
            searchQuery = "Resultados para \"$searchQuery\"",
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun SearchHeader(
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Search Icon",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Buscar \"$searchQuery\"",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun CategoryChipsComponent(
    categories: List<String>,
    modifier: Modifier = Modifier,
    onCategorySelected: (String) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(categories[1]) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = {
                        selectedCategory = category
                        onCategorySelected(category)
                    },
                    label = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = category,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    },
                    modifier = Modifier
                        .height(38.dp)
                        .weight(3f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    navController: NavController,
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            placeholder = {
                Text(
                    text = "Buscar...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 2.dp,
                    color = if (isFocused.value) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused.value = it.isFocused },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            singleLine = true
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


@Composable
fun CategoryChipsComponent(categories: List<String>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Chip(category = category)
        }
    }
}

@Composable
fun Chip(category: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    SearchView(navController = navController, innerPadding = innerPadding)
}