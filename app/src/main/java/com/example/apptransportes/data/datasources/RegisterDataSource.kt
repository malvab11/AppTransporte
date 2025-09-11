package com.example.apptransportes.data.datasources

import com.example.apptransportes.data.models.RegisterDataModel
import com.example.apptransportes.data.models.RegisterTypeModel
import com.example.apptransportes.data.models.UserModel

interface RegisterDataSource {
    suspend fun getRegisterType(): List<RegisterTypeModel?>
    suspend fun getUserByDni(dni: String): UserModel?

    suspend fun registerInfo(data: List<RegisterDataModel>): List<String>
}