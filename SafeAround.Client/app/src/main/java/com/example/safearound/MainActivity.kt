package com.example.safearound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safearound.components.DrawerMenu
import com.example.safearound.modules.UserLocationViewModel
import com.example.safearound.pages.Home
import com.example.safearound.pages.Incidents
import com.example.safearound.ui.theme.SafeAroundTheme
import com.example.safearound.viewmodels.MapViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SafeAroundTheme(
                darkTheme = false
            ) {
                val navController = rememberNavController()

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                val displayErrorSnackbar: (String) -> Unit = { message ->
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }

                DrawerMenu(navController, drawerState) {

                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        },
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(imageVector = Icons.Sharp.Menu, contentDescription = "Home")
                                    }
                                },
                                colors = topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary
                                ),
                                title = {
                                    Text("SafeAround")
                                },
                            )
                        },
                    ) { _ ->
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") { Home(MapViewModel(), UserLocationViewModel(), displayErrorSnackbar) }
                            composable("incidents") { Incidents() }
                        }
                    }
                }
            }
        }
    }
}