package com.example.apptransportes.data.mappers

import com.example.apptransportes.data.models.RoutesModel
import com.example.apptransportes.domain.entity.RoutesEntity

fun RoutesModel.toEntity(): RoutesEntity = RoutesEntity(
    routeId = routeId,
    descripcion = descripcion,
    empresaId = empresaId,
    nombre = nombre
)

fun RoutesModel.toModel(): RoutesModel = RoutesModel(
    routeId = routeId,
    descripcion = descripcion,
    empresaId = empresaId,
    nombre = nombre
)