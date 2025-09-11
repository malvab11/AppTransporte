package com.example.apptransportes.presentation.ui.screens.register

import com.example.apptransportes.domain.entity.RegisterTypeEntity
import com.example.apptransportes.domain.entity.UserEntity

data class RegisterUiState(
    //Datos
    val registerTypes: List<RegisterTypeEntity> = emptyList(),
    val selectedRegisterType: RegisterTypeEntity? = null,
    val dni: String = "",
    val usersList: List<UserEntity> = emptyList(),
    val user: UserEntity? = null,
    val registerTime: Long = System.currentTimeMillis(),

    //Booleanos de UI
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,

    //Valores de Alert Dialog
    val activeAlertDialog: Boolean = false,
    val activeTimeDialog: Boolean = false,
 )
