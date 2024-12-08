package com.example.dbapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun BottomAppBarNew(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val activeColor = MaterialTheme.colorScheme.primary
    val defaultColor = Color.Gray
    val buttonSize = 56.dp

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.size(buttonSize)
            ) {
                val isActive = currentRoute == "home"
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = if (isActive) activeColor else defaultColor)
                    Text(text = "Home", fontSize = 12.sp, color = if (isActive) activeColor else defaultColor)
                }
            }
            IconButton(
                onClick = { navController.navigate("orders") },
                modifier = Modifier.size(buttonSize)
            ) {
                val isActive = currentRoute == "orders"
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Orders", tint = if (isActive) activeColor else defaultColor)
                    Text(text = "Orders", fontSize = 12.sp, color = if (isActive) activeColor else defaultColor)
                }
            }
            IconButton(
                onClick = { navController.navigate("product") },
                modifier = Modifier.size(buttonSize)
            ) {
                val isActive = currentRoute == "product"
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Place, contentDescription = "Product", tint = if (isActive) activeColor else defaultColor)
                    Text(text = "Product", fontSize = 12.sp, color = if (isActive) activeColor else defaultColor)
                }
            }
            IconButton(
                onClick = { navController.navigate("client") },
                modifier = Modifier.size(buttonSize)
            ) {
                val isActive = currentRoute == "client"
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Person, contentDescription = "Client", tint = if (isActive) activeColor else defaultColor)
                    Text(text = "Client", fontSize = 12.sp, color = if (isActive) activeColor else defaultColor)
                }
            }
            IconButton(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.size(buttonSize)
            ) {
                val isActive = currentRoute == "menu"
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = if (isActive) activeColor else defaultColor)
                    Text(text = "More", fontSize = 12.sp, color = if (isActive) activeColor else defaultColor)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    BottomAppBarNew(navController)
}
