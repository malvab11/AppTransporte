package com.example.apptransportes.data.datasources

import com.example.apptransportes.data.models.CompaniesModel
import com.example.apptransportes.data.models.PointsModel
import com.example.apptransportes.data.models.RoutesModel

interface MainDataSource {
    suspend fun getCompanies(): List<CompaniesModel?>
    suspend fun getRoutes(companyId: String): List<RoutesModel?>
    suspend fun getPoints(companyId: String, routeId: String): List<PointsModel?>
}