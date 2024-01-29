package ru.popkov.rtl_support_app.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.popkov.rtl_support_app.screens.ComposeScreen
import ru.popkov.rtl_support_app.screens.MainScreen
import ru.popkov.rtl_support_app.screens.XMLScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Main.route) {
        composable(route = Screens.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.XML.route) {
            XMLScreen()
        }
        composable(route = Screens.Compose.route) {
            ComposeScreen(navController = navController)
        }
    }
}