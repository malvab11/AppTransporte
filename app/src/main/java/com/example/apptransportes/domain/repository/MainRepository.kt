package com.example.apptransportes.domain.repository

import com.example.apptransportes.data.models.ConfigurationModel
import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.entity.ConfigurationEntity
import com.example.apptransportes.domain.entity.PointsEntity
import com.example.apptransportes.domain.entity.RoutesEntity

interface MainRepository {
    //Getters
    suspend fun getCompanies(): Result<List<CompaniesEntity>>
    suspend fun getRoutes(companyId: String): Result<List<RoutesEntity>>
    suspend fun getPoints(companyId: String, routeId: String): Result<List<PointsEntity>>

    //Setters
    suspend fun setConfiguration(configuration: ConfigurationEntity): Result<String>
}