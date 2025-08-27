package com.example.apptransportes.presentation.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransportes.domain.usecase.auth.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<LoginEvent>()
    val uiEvents: SharedFlow<LoginEvent> = _uiEvents.asSharedFlow()

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

    fun onIconPressed() {
        _uiState.update {
            it.copy(isPasswordHide = !it.isPasswordHide)
        }
    }

    fun onLogginPressed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val currentState = _uiState.value
            val response =
                loginUserUseCase(dni = currentState.dni, password = currentState.password)

            response.fold(
                onSuccess = { user ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                    _uiEvents.emit(LoginEvent.Success("Bienvenido ${user.nombres}"))
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message,
                        )
                    }
                    _uiEvents.emit(LoginEvent.Error)

                }
            )
        }
    }

}