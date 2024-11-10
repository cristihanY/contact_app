package com.example.dbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.navigation.AppNavGraph
import com.example.dbapp.viewmodel.ContactViewModel
import com.example.dbapp.ui.theme.DbAppTheme
import com.example.dbapp.ui.component.BottomAppBarNew
import com.example.dbapp.ui.home.HomeView


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val contactViewModel = ContactViewModel()

            val hideBottomBarRoutes = listOf("createContact", "scanner")

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
                        contactViewModel = contactViewModel
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        val navController = rememberNavController()
        val innerPadding = PaddingValues(16.dp) // o el valor que necesites
        HomeView(navController = navController, innerPadding = innerPadding)
    }

}