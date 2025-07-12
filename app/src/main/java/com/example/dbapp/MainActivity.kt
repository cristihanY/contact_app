package com.example.dbapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import com.example.dbapp.ui.theme.DbAppTheme
import com.example.dbapp.navigation.AppNavGraph
import androidx.compose.ui.tooling.preview.Preview
import com.example.dbapp.ui.client.ContactViewModel
import com.example.dbapp.ui.component.BottomAppBarNew
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dbapp.ui.cart.CartItemViewModel
import com.example.dbapp.ui.cart.CartViewModel
import com.example.dbapp.ui.home.HomeView
import com.example.dbapp.ui.orders.OrderViewModel
import com.example.dbapp.ui.product.ProductViewModel
import com.example.dbapp.ui.uiutil.MessageSnackbar
import com.example.dbapp.viewmodel.MessageServiceViewModel


class MainActivity : ComponentActivity() {

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val messageViewModel = MessageServiceViewModel()
            val contactViewModel = ContactViewModel(messageViewModel)
            val productViewModel = ProductViewModel(messageViewModel)
            val cartViewModel = CartViewModel(messageViewModel)
            val cartItemViewModel = CartItemViewModel(messageViewModel)
            val orderViewModel = OrderViewModel(messageViewModel)

            val hideBottomBarRoutes = listOf("createContact", "scanner", "editCustomer/{customerId}",
                "createProduct", "productDetails/{productId}", "search", "cart",
                "cartItem/{cartItemId}", "pay", "pay_option")

            DbAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val currentRoute by navController.currentBackStackEntryAsState()
                        if (currentRoute?.destination?.route !in hideBottomBarRoutes) {
                            BottomAppBarNew(navController)
                        }
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        innerPadding = innerPadding,
                        contactViewModel = contactViewModel,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel,
                        cartItemViewModel = cartItemViewModel,
                        orderViewModel = orderViewModel
                    )
                    MessageSnackbar(viewModel = messageViewModel)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        val navController = rememberNavController()
        val innerPadding = PaddingValues(16.dp)
        HomeView(navController = navController, innerPadding = innerPadding)
    }

}