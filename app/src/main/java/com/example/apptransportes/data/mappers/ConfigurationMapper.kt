package com.example.apptransportes.data.mappers

import com.example.apptransportes.data.models.ConfigurationModel
import com.example.apptransportes.domain.entity.ConfigurationEntity

fun ConfigurationModel.toEntity(): ConfigurationEntity = ConfigurationEntity(
    date = date,
    companyId = companyId,
    routeId = routeId
)

fun ConfigurationEntity.toModel(): ConfigurationModel = ConfigurationModel(
    date = date,
    companyId = companyId,
    routeId = routeId
)