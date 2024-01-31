package ru.popkov.rtl_support_app.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.popkov.rtl_support_app.databinding.ActivityMainBinding
import ru.popkov.rtl_support_app.screens.ComposeScreen
import ru.popkov.rtl_support_app.screens.main.MainFragment
import ru.popkov.rtl_support_app.screens.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Main.route) {
        composable(route = Screens.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.XML.route) {
            AndroidViewBinding(ActivityMainBinding::inflate) {
                container.getFragment<MainFragment>()
            }
        }
        composable(route = Screens.Compose.route) {
            ComposeScreen(navController = navController)
        }
    }
}