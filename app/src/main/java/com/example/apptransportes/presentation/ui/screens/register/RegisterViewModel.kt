package com.example.apptransportes.presentation.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransportes.domain.entity.RegisterTypeEntity
import com.example.apptransportes.domain.usecase.register.GetRegisterTypesUseCase
import com.example.apptransportes.domain.usecase.register.GetUserByDniUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getRegisterTypesUseCase: GetRegisterTypesUseCase,
    private val getUserByDniUseCase: GetUserByDniUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    // ------------------------------
    // Manejo de diálogos
    // ------------------------------
    fun showDialog() {
        _uiState.update { it.copy(activeAlertDialog = true) }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(activeAlertDialog = false) }
    }

    fun showTimeDialog() {
        _uiState.update { it.copy(activeTimeDialog = true) }
    }

    fun dismissTimeDialog() {
        _uiState.update { it.copy(activeTimeDialog = false) }
    }

    // ------------------------------
    // Seteo de datos
    // ------------------------------
    fun onDniChange(dni: String) {
        _uiState.update {
            it.copy(
                dni = dni
            )
        }
    }

    // ------------------------------
    // Carga de datos
    // ------------------------------

    fun getRegisterTypes() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null, successMessage = null)
            }

            val response = getRegisterTypesUseCase()
            response.fold(
                onSuccess = { types ->
                    _uiState.update {
                        it.copy(isLoading = false, registerTypes = types, errorMessage = null)
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message)
                    }
                }

            )
        }
    }

    private fun getUserByDni() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val response = getUserByDniUseCase(dni = _uiState.value.dni)
            response.fold(
                onSuccess = { userData ->
                    _uiState.update {
                        it.copy(
                            user = userData,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    // ------------------------------
    // Selección de elementos
    // ------------------------------

    fun onTypeSelected(registerType: RegisterTypeEntity) {
        _uiState.update {
            it.copy(selectedRegisterType = registerType)
        }
    }
}