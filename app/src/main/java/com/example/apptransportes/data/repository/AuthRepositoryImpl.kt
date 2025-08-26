package com.example.apptransportes.data.repository

import com.example.apptransportes.data.datasources.auth.AuthDataSource
import com.example.apptransportes.data.mappers.toEntity
import com.example.apptransportes.domain.entity.UserEntity
import com.example.apptransportes.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(dni: String, password: String): Result<UserEntity> {
        return try {
            val user = remoteDataSource.login(dni = dni, password = password)
            if (user != null) {
                Result.success(user.toEntity())
            } else {
                Result.failure(Exception("Credenciales Incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}