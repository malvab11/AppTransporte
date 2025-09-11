package com.example.apptransportes.data.mappers

import com.example.apptransportes.data.models.RegisterTypeModel
import com.example.apptransportes.domain.entity.RegisterTypeEntity

fun RegisterTypeModel.toEntity() : RegisterTypeEntity = RegisterTypeEntity(
    uid = uid,
    type = type
)
fun RegisterTypeEntity.toModel() : RegisterTypeModel = RegisterTypeModel(
    uid = uid,
    type = type
)