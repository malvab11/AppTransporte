package com.example.apptransportes.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apptransportes.presentation.ui.screens.auth.LoginScreen
import com.example.apptransportes.presentation.ui.screens.home.HomeScreen
import com.example.apptransportes.presentation.ui.screens.loading.LoadingScreen
import com.example.apptransportes.presentation.ui.screens.register.RegisterScreen

@Composable
fun AppNavigation(padding: PaddingValues) {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeScreen.route
    ) {
        composable(Routes.LoginScreen.route) {
            LoginScreen(padding = padding) {
                navigationController.navigate("loadingScreen") {
                    popUpTo("loginScreen") { inclusive = true }
                }
            }
        }
        composable(Routes.LoadingScreen.route) {
            LoadingScreen(padding = padding) {
                navigationController.navigate("homeScreen") {
                    popUpTo("loadingScreen") { inclusive = true }
                }
            }
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen(padding = padding)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(padding = padding)
        }
    }
}