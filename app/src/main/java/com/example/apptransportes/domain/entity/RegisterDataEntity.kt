package com.example.apptransportes.domain.entity

data class RegisterDataEntity(
    val uid: String? = null,
    val users: UserEntity,
    val registerTime: Long
)