package com.example.apptransportes.data.mappers

import com.example.apptransportes.data.models.UserModel
import com.example.apptransportes.domain.entity.UserEntity

fun UserModel.toEntity(): UserEntity = UserEntity(
    uid = uid,
    nombres = nombres,
    apellidos = apellidos,
    rol = rol
)

fun UserEntity.toModel(): UserModel = UserModel(
    uid = uid,
    nombres = nombres,
    apellidos = apellidos,
    rol = rol
)