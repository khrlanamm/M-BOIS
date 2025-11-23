package com.kuartet.mbois.navigation

sealed class Screen(val route: String) {
    data object OnBoarding : Screen("onboarding")
    data object Auth : Screen("auth")
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Detail : Screen("detail/{cardId}") {
        fun createRoute(cardId: String) = "detail/$cardId"
    }
}