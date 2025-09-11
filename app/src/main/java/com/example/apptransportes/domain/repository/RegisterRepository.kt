package com.example.apptransportes.domain.repository

import com.example.apptransportes.domain.entity.RegisterDataEntity
import com.example.apptransportes.domain.entity.RegisterTypeEntity
import com.example.apptransportes.domain.entity.UserEntity

interface RegisterRepository {
    suspend fun getRegisterType(): Result<List<RegisterTypeEntity>>
    suspend fun getUserByDni(dni: String):Result<UserEntity>

    suspend fun registerInfo(data: RegisterDataEntity): Result<String>
}