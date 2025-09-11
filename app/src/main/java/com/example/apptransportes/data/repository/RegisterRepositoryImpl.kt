package com.example.apptransportes.data.repository

import com.example.apptransportes.data.datasources.RegisterDataSource
import com.example.apptransportes.data.mappers.toEntity
import com.example.apptransportes.domain.entity.RegisterDataEntity
import com.example.apptransportes.domain.entity.RegisterTypeEntity
import com.example.apptransportes.domain.entity.UserEntity
import com.example.apptransportes.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterRepository {
    override suspend fun getRegisterType(): Result<List<RegisterTypeEntity>> {
        return try {
            val registerTypes = registerDataSource.getRegisterType()
            if (!registerTypes.isEmpty()) {
                Result.success(registerTypes.map { it!!.toEntity() })
            } else {
                Result.failure(Exception("No se pudo cargar la información, volver a intentar."))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getUserByDni(dni: String): Result<UserEntity> {
        return try {
            val user = registerDataSource.getUserByDni(dni = dni)
            if (user != null) {
                Result.success(user.toEntity())
            } else {
                Result.failure(Exception("No se encontro al usuario en la base de datos, favor de registrarlo o intentar de nuevo."))
            }
        } catch (e: Exception) {
            Result.failure<UserEntity>(exception = e)
        }
    }

    override suspend fun registerInfo(data: RegisterDataEntity): Result<String> {
        return try {
            val response =
        } catch (e: Exception){
            Result.failure<String>(exception = e)
        }
    }
}