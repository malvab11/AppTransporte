package com.example.apptransportes.domain.repository

import com.example.apptransportes.domain.entity.UserEntity

interface AuthRepository {
    suspend fun login(dni: String, password: String): Result<UserEntity>
}