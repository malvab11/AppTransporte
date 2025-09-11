package com.example.apptransportes.data.mappers

import com.example.apptransportes.data.models.PointsModel
import com.example.apptransportes.data.models.RoutesModel
import com.example.apptransportes.domain.entity.PointsEntity
import com.example.apptransportes.domain.entity.RoutesEntity

fun PointsModel.toEntity(): PointsEntity = PointsEntity(
    pointId = pointId,
    direccion = direccion,
    empresaId = empresaId,
    nombre = nombre,
    rutaId = rutaId
)

fun PointsEntity.toModel(): PointsModel = PointsModel(
    pointId = pointId,
    direccion = direccion,
    empresaId = empresaId,
    nombre = nombre,
    rutaId = rutaId
)