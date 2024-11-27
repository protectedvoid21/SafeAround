package com.example.safearound.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class DrawerDataEntry(val label: String, val icon: ImageVector, val route: String)

val drawerItems = listOf(
    DrawerDataEntry("Mapa", Icons.Filled.Home, "home"),
    DrawerDataEntry("Lista zgłoszeń", Icons.Filled.Info, "incidents"),
    DrawerDataEntry("Twoje zgłoszenia", Icons.Sharp.AccountCircle, "your-incidents"),
    DrawerDataEntry("Raporty", Icons.Filled.DateRange, "reports")
)

var bottomDrawerItems = listOf(
    DrawerDataEntry("Ustawienia", Icons.Filled.Settings, "settings"),
    DrawerDataEntry("Zgłoś uwagę", Icons.Filled.MailOutline, "report-issue")
)

@Composable
fun DrawerItem (drawerData: DrawerDataEntry, navController: NavController) {
    NavigationDrawerItem(
        icon = { Icon(drawerData.icon, contentDescription = drawerData.label) },
        label = { Text(text = drawerData.label, fontSize = 18.sp) },
        selected = false,
        onClick = {
            navController.navigate(drawerData.route)
        }
    )
}

@Composable
fun DrawerMenu(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "SafeAround",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
                HorizontalDivider()

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        drawerItems.forEach { DrawerItem(it, navController) }
                    }
                    Column {
                        bottomDrawerItems.forEach { DrawerItem(it, navController) }
                    }
                }
            }
        },
    ) {
        content()
    }
}

@Preview
@Composable
fun DrawerMenuPreview() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Open)
    DrawerMenu(navController, drawerState) {
        Button(onClick = {  }) {
            Text(text = "Open Drawer")
        }
    }
}