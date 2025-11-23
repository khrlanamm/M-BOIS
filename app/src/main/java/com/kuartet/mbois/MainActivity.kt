package com.kuartet.mbois

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.kuartet.mbois.navigation.Screen
import com.kuartet.mbois.ui.screens.AuthScreen
import com.kuartet.mbois.ui.screens.DetailScreen
import com.kuartet.mbois.ui.screens.HomeScreen
import com.kuartet.mbois.ui.screens.InteractiveScreen
import com.kuartet.mbois.ui.screens.OnBoardingScreen
import com.kuartet.mbois.ui.screens.ProfileScreen
import com.kuartet.mbois.ui.theme.MBOISTheme
import com.kuartet.mbois.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MBOISTheme {
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()

                val homeViewModel: HomeViewModel = viewModel()

                var startDestination by remember {
                    mutableStateOf(
                        if (auth.currentUser != null) Screen.Home.route else Screen.OnBoarding.route
                    )
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(Screen.OnBoarding.route) {
                        OnBoardingScreen(
                            onComplete = {
                                navController.navigate(Screen.Auth.route) {
                                    popUpTo(Screen.OnBoarding.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.Auth.route) {
                        AuthScreen(
                            onLoginSuccess = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Auth.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.Home.route) {
                        HomeScreen(
                            viewModel = homeViewModel,
                            onNavigateToProfile = {
                                navController.navigate(Screen.Profile.route)
                            },
                            onNavigateToDetail = { cardId ->
                                navController.navigate(Screen.Detail.createRoute(cardId))
                            }
                        )
                    }

                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            onBack = {
                                navController.popBackStack()
                            },
                            onLogout = {
                                auth.signOut()
                                navController.navigate(Screen.OnBoarding.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

                    composable(
                        route = Screen.Detail.route,
                        arguments = listOf(navArgument("cardId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val cardId = backStackEntry.arguments?.getString("cardId") ?: ""
                        DetailScreen(
                            cardId = cardId,
                            viewModel = homeViewModel,
                            onBack = {
                                navController.popBackStack()
                            },
                            onNavigateToInteractive = { id ->
                                navController.navigate(Screen.Interactive.createRoute(id))
                            }
                        )
                    }

                    composable(
                        route = Screen.Interactive.route,
                        arguments = listOf(navArgument("cardId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val cardId = backStackEntry.arguments?.getString("cardId") ?: ""
                        InteractiveScreen(
                            cardId = cardId,
                            viewModel = homeViewModel,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}