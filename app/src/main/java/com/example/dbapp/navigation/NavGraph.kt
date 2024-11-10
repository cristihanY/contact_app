package com.example.dbapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dbapp.viewmodel.ContactViewModel
import com.example.dbapp.ui.client.ClientView
import com.example.dbapp.ui.client.ContactFormScreen
import com.example.dbapp.ui.client.EditContactFormScreen
import com.example.dbapp.ui.home.HomeView
import com.example.dbapp.ui.main.MainScreen
import com.example.dbapp.ui.menu.MenuView
import com.example.dbapp.ui.orders.OrderView
import com.example.dbapp.ui.product.ProductView
import com.example.dbapp.ui.scanner.ScannerView


@Composable
fun AppNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    contactViewModel: ContactViewModel
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
        composable("scanner") {
            ScannerView(
                navController =navController,
                innerPadding = innerPadding
            )
        }
        composable("orders") { OrderView(
            navController =navController
        ) }
        composable("product") { ProductView(
            navController =navController,
            innerPadding = innerPadding
        ) }
        composable("client") { ClientView(
            navController =navController,
            contactViewModel = contactViewModel,
            innerPadding = innerPadding
        ) }
        composable("menu") { MenuView(navController) }

        composable("createContact") {

            ContactFormScreen(
                navController = navController,
                innerPadding = innerPadding,
                onSave = { customer -> contactViewModel.addCustomer(customer) }
            )
        }

        composable("editCustomer/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toLongOrNull() ?: 0L

            println("Received customer ID: $customerId")

            EditContactFormScreen(
                navController = navController,
                innerPadding = PaddingValues(16.dp),
                customerId = customerId,
                viewModel = contactViewModel
            )
        }


    }
}