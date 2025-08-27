package com.example.apptransportes.presentation.ui.screens.auth

sealed class LoginEvent {
    data class Success(val message: String): LoginEvent()
    object Error : LoginEvent()
}