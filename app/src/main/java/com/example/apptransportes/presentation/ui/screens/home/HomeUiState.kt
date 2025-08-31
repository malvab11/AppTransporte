package com.example.apptransportes.presentation.ui.screens.home

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.entity.RoutesEntity

data class HomeUiState(
    //Datos
    val fecha: Long? = null,
    val listEmpresas: List<CompaniesEntity> = emptyList(),
    val empresa: CompaniesEntity? = null,
    val listRutas: List<RoutesEntity> = emptyList(),
    val ruta: RoutesEntity? = null,

    //Booleanos de UI
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,

    //Valores de Alert Dialog
    val activeDialog:HomeDialogType = HomeDialogType.NONE,
    val activeDateDialog:Boolean = false
)

data class ConfigurationFields(
    val value: String,
    val enabled: Boolean,
    val preIcon: ImageVector,
    val onClick: () -> Unit
)

enum class HomeDialogType {
    NONE, EMPRESAS, RUTAS
}