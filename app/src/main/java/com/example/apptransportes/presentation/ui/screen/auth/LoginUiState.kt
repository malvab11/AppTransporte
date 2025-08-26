package com.example.apptransportes.presentation.ui.screen.auth

data class LoginUiState(
    val dni: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordHide: Boolean = true,
    val isEnabled: Boolean = false,
    val errorMessage: String? = ""
)