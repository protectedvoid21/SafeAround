package com.example.safearound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safearound.components.DrawerMenu
import com.example.safearound.pages.Map
import com.example.safearound.pages.Reports
import com.example.safearound.ui.theme.SafeAroundTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SafeAroundTheme {
                val navController = rememberNavController()

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                DrawerMenu(navController, drawerState) {
                    Scaffold(
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
                        floatingActionButton = {
                            Button(
                                onClick = { },
                                modifier = Modifier.clip(RoundedCornerShape(200.dp))
                            ) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                            }
                        }
                    ) { _ ->
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") { Map(navController) }
                            composable("reports") { Reports() }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RoundedButtonPreview() {
    OutlinedButton(onClick = {},
        modifier = Modifier.clip(RoundedCornerShape(200.dp))) { }
    /*Button(
        onClick = { },
        shape = CircleShape,
    ) {*//*
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add",
            modifier = Modifier.padding(100.dp)
        )*//*
    }*/
}