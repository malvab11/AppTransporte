package com.example.apptransportes.presentation.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.usecase.home.GetCompaniesUseCase
import com.example.apptransportes.domain.usecase.home.GetPointsUseCase
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
    private val getPointsUseCase: GetPointsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getCompanies() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
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

    fun onCompanySelected(company: CompaniesEntity){
        _uiState.update {
            it.copy(
                empresa = company
            )
        }
    }

    fun getRoutesByCompany() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val response = getRoutesByCompanyUseCase(companyId = _uiState.value.empresa?.empresaId
                ?: "")


            response.fold(
                onSuccess = { routes ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listRutas = routes,
                            errorMessage = null
                        )
                    }
                    Log.i("Rutas", routes.first().toString())
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

//    fun getPoints() {
//        viewModelScope.launch {
//            _uiState.update {
//                it.copy(
//                    isLoading = true,
//                    errorMessage = null
//                )
//            }
//            val response = getPointsUseCase(companyId = _uiState.value.empresa, routeId = _uiState.value.ruta)
//
//            response.fold(
//                onSuccess = { points ->
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            errorMessage = null
//                        )
//                    }
//                    Log.i("Puntos de recojo", points.first().toString())
//                },
//                onFailure = { error ->
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            errorMessage = error.message
//                        )
//                    }
//
//                }
//            )
//
//        }
//    }
}