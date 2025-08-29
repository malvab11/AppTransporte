package com.example.apptransportes.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.entity.RoutesEntity
import com.example.apptransportes.domain.usecase.home.GetCompaniesUseCase
import com.example.apptransportes.domain.usecase.home.GetRoutesByCompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCompaniesUseCase: GetCompaniesUseCase,
    private val getRoutesByCompanyUseCase: GetRoutesByCompanyUseCase,
) : ViewModel() {

    // Estado privado y público
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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
            _uiState.update { it.copy(isLoading = true, errorMessage = null, listRutas = emptyList()) }

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
                isEnabled = false
            )
        }
    }

    fun onRouteSelected(route: RoutesEntity) {
        _uiState.update {
            it.copy(
                ruta = route,
                isEnabled = !route.equals(null)
            )
        }
    }

    // ------------------------------
    // Envio de datos
    // ------------------------------

    fun onSaveClick(){

    }
}
