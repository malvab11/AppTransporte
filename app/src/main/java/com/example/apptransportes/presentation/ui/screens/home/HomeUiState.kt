package com.example.apptransportes.presentation.ui.screens.home

import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.entity.RoutesEntity

data class HomeUiState(
    //Datos
    val fecha: String ="",
    val listEmpresas: List<CompaniesEntity> = emptyList(),
    val empresa: CompaniesEntity? = null,
    val listRutas: List<RoutesEntity> = emptyList(),
    val ruta: RoutesEntity? = null,

    //Booleanos de UI
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
