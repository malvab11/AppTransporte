package com.example.apptransportes.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.entity.ConfigurationEntity
import com.example.apptransportes.domain.entity.RoutesEntity
import com.example.apptransportes.domain.usecase.home.GetCompaniesUseCase
import com.example.apptransportes.domain.usecase.home.GetRoutesByCompanyUseCase
import com.example.apptransportes.domain.usecase.home.SetConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCompaniesUseCase: GetCompaniesUseCase,
    private val getRoutesByCompanyUseCase: GetRoutesByCompanyUseCase,
    private val setConfigurationUseCase: SetConfigurationUseCase
) : ViewModel() {

    // Estado privado y público
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    //Estado Inicial
    fun clearMessages() {
        _uiState.update {
            it.copy(
                successMessage = null,
                errorMessage = null,
            )
        }
    }

    // ------------------------------
    // Manejo de diálogos
    // ------------------------------
    fun showDialog(type: HomeDialogType) {
        _uiState.update { it.copy(activeDialog = type) }

        when (type) {
            HomeDialogType.EMPRESAS -> getCompanies()
            HomeDialogType.RUTAS -> getRoutesByCompany()
            else -> {}
        }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(activeDialog = HomeDialogType.NONE) }
    }

    fun showDateDialog() {
        _uiState.update { it.copy(activeDateDialog = true) }
    }

    fun dismissDateDialog() {
        _uiState.update { it.copy(activeDateDialog = false) }
    }

    // ------------------------------
    // Carga de datos
    // ------------------------------
    fun getCompanies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val response = getCompaniesUseCase()
            response.fold(
                onSuccess = { companies ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listEmpresas = companies,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    fun getRoutesByCompany() {
        val companyId = _uiState.value.empresa?.empresaId ?: return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    listRutas = emptyList()
                )
            }

            val response = getRoutesByCompanyUseCase(companyId)
            response.fold(
                onSuccess = { routes ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listRutas = routes,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    // ------------------------------
    // Selección de elementos
    // ------------------------------
    fun onCompanySelected(company: CompaniesEntity) {
        _uiState.update {
            it.copy(
                empresa = company,
                ruta = null,
                listRutas = emptyList(),
            )
        }
        isEnabledValidator()
    }

    fun onRouteSelected(route: RoutesEntity) {
        _uiState.update {
            it.copy(
                ruta = route,
            )
        }
        isEnabledValidator()
    }

    fun onDateSelected(date: Long) {
        _uiState.update {
            it.copy(
                fecha = date,
            )
        }
        isEnabledValidator()
    }

    fun dateText(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")

        val fechaSeleccionada = _uiState.value.fecha
        val fechaInicial = fechaSeleccionada ?: System.currentTimeMillis()

        return dateFormat.format(Date(fechaInicial))
    }

    // ------------------------------
    // Seteo de Datos
    // ------------------------------

    private fun isEnabledValidator() {
        _uiState.update {
            it.copy(
                isEnabled =
                            it.fecha != null &&
                            it.empresa?.empresaId != null &&
                            it.ruta?.routeId != null
            )
        }
    }

    // ------------------------------
    // Envio de datos
    // ------------------------------

    fun onSaveClick() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val currentState = _uiState.value
            val result = setConfigurationUseCase(
                configuration = ConfigurationEntity(
                    date = currentState.fecha!!,
                    companyId = currentState.empresa!!.empresaId,
                    routeId = currentState.ruta!!.routeId
                )
            )
            result.fold(
                onSuccess = { setResult ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = setResult,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { setResult ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = setResult.message
                        )
                    }
                }
            )
        }
    }
}
