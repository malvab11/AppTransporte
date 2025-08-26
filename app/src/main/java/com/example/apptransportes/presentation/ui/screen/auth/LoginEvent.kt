package com.example.apptransportes.presentation.ui.screen.auth

sealed class LoginEvent {
    data class Success(val message: String): LoginEvent()
    object Error : LoginEvent()
}