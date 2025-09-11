package com.example.apptransportes.data.mappers

import com.example.apptransportes.data.models.CompaniesModel
import com.example.apptransportes.domain.entity.CompaniesEntity

fun CompaniesModel.toEntity(): CompaniesEntity = CompaniesEntity(
    empresaId = empresaId,
    nombre = nombre,
    email = email,
    direccion = direccion,
    telefono = telefono
)

fun CompaniesModel.toModel(): CompaniesModel = CompaniesModel(
    empresaId = empresaId,
    nombre = nombre,
    email = email,
    direccion = direccion,
    telefono = telefono
)