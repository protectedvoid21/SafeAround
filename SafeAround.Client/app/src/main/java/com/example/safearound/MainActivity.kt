package com.example.safearound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safearound.components.DrawerMenu
import com.example.safearound.pages.Home
import com.example.safearound.pages.Reports
import com.example.safearound.ui.theme.SafeAroundTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SafeAroundTheme {
                val navController = rememberNavController()

                DrawerMenu(navController, remember { mutableStateOf(false) }) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary
                                ),
                                title = {
                                    Text("SafeAround")
                                },
                            )
                        }
                    ) { _ ->
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") { Home(navController) }
                            composable("reports") { Reports() }
                        }
                    }
                }
            }
        }
    }
}