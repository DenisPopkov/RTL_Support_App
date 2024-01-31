package ru.popkov.rtl_support_app.screens.navigation

sealed class Screens(val route: String) {
    data object Main : Screens("main_screen")
    data object Compose : Screens("compose_screen")
    data object XML : Screens("xml_screen")
}