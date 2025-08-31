package com.example.apptransportes.data.models

data class ConfigurationModel(
    val date: Long = System.currentTimeMillis(),
    val companyId: String = "",
    val routeId: String = "",
    val dateEdition: Long = System.currentTimeMillis()
)
