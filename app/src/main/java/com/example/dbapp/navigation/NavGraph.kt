package com.example.dbapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dbapp.ui.cart.CartItemViewModel
import com.example.dbapp.ui.cart.CartScreen
import com.example.dbapp.ui.cart.CartViewModel
import com.example.dbapp.ui.cart.EditCartFormScreen
import com.example.dbapp.ui.cart.PayOptionScreen
import com.example.dbapp.ui.cart.PayScreen
import com.example.dbapp.ui.client.ContactViewModel
import com.example.dbapp.ui.client.ClientView
import com.example.dbapp.ui.client.CreateContactFormScreen
import com.example.dbapp.ui.client.EditContactFormScreen
import com.example.dbapp.ui.home.HomeView
import com.example.dbapp.ui.main.MainScreen
import com.example.dbapp.ui.menu.MenuView
import com.example.dbapp.ui.orders.OrderView
import com.example.dbapp.ui.orders.OrderViewModel
import com.example.dbapp.ui.product.CreateProductFormScreen
import com.example.dbapp.ui.product.EditProductFormScreen
import com.example.dbapp.ui.product.ProductView
import com.example.dbapp.ui.product.ProductViewModel
import com.example.dbapp.ui.scanner.ScannerView
import com.example.dbapp.ui.search.SearchView


@Composable
fun AppNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    contactViewModel: ContactViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    cartItemViewModel: CartItemViewModel,
    orderViewModel: OrderViewModel,
) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                navController = navController,
                contactViewModel = contactViewModel
            )
        }
        composable("home") {
            HomeView(
                navController =navController,
                innerPadding = innerPadding
            )
        }

        composable("search") {
            SearchView(
                navController =navController,
                innerPadding = innerPadding
            )
        }

        composable("scanner") {
            ScannerView(
                navController =navController,
                innerPadding = innerPadding
            )
        }
        composable("orders") { OrderView(
            navController =navController,
            viewModel = orderViewModel,
            innerPadding = innerPadding
        ) }

        composable("product") { ProductView(
            navController =navController,
            productViewModel = productViewModel,
            innerPadding = innerPadding
        ) }

        composable("productDetails/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull() ?: 0L

            EditProductFormScreen(
                navController = navController,
                innerPadding = innerPadding,
                productId = productId,
                viewModel = productViewModel
            )
        }

        composable("createProduct") {
            CreateProductFormScreen(
                navController = navController,
                innerPadding = innerPadding,
                onSave = { product -> productViewModel.addProduct(product) }
            )
        }

        composable("client") { ClientView(
            navController =navController,
            contactViewModel = contactViewModel,
            innerPadding = innerPadding
        ) }

        composable("menu") { MenuView(navController) }

        composable("createContact") {

            CreateContactFormScreen(
                navController = navController,
                innerPadding = innerPadding,
                onSave = { customer -> contactViewModel.addCustomer(customer) }
            )
        }

        composable("editCustomer/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toLongOrNull() ?: 0L

            EditContactFormScreen(
                navController = navController,
                innerPadding = innerPadding,
                customerId = customerId,
                viewModel = contactViewModel
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = cartViewModel,
                onSave = {}
            )
        }

        composable("cartItem/{cartItemId}") { backStackEntry ->
            val cartItemId = backStackEntry.arguments?.getString("cartItemId")?.toLongOrNull() ?: 0L

            EditCartFormScreen(
                navController = navController,
                innerPadding = innerPadding,
                cartItemId = cartItemId,
                viewModel = cartItemViewModel
            )
        }

        composable("pay") {
            PayScreen(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = cartViewModel
            )
        }

        composable("pay_option") {
            PayOptionScreen(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = cartViewModel
            )
        }

    }
}