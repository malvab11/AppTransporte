package com.example.apptransportes.presentation.ui.navigation

sealed class Routes(val route: String){
    data object LoginScreen : Routes(route = "loginScreen")
    data object LoadingScreen : Routes(route = "loadingScreen")
    data object HomeScreen : Routes(route = "homeScreen")
    data object RegisterScreen : Routes(route = "registerScreen")
}