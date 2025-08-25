package com.example.apptransportes.presentation.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUserChange(dni: String) {
        _uiState.update {
            it.copy(
                dni = dni,
                isEnabled = dni.isNotEmpty() && it.password.length >= 6
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                isEnabled = it.dni.isNotEmpty() && password.length >= 6
            )
        }
    }

    fun onIconPressed(){
        _uiState.update {
            it.copy(isPasswordHide = !it.isPasswordHide)
        }
    }


    fun onLogginPressed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val currentState = _uiState.value

                if (currentState.dni == "admin" && currentState.password == "123456") {
                    // ✅ Login correcto
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Logueado con exito",
                            isLoggedIn = true // 👈 agrega esta flag en tu LoginUiState
                        )
                    }
                } else {
                    // ❌ Error de credenciales
                    throw Exception("Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error desconocido",
                        isLoggedIn = false
                    )
                }
            }
        }
    }

}