package com.example.apptransportes.data.datasources

import com.example.apptransportes.data.models.UserModel

interface AuthDataSource {
    suspend fun login(dni: String, password: String): UserModel?
}