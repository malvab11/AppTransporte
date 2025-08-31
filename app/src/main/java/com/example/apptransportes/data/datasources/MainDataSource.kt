package com.example.apptransportes.data.datasources

import com.example.apptransportes.data.models.CompaniesModel
import com.example.apptransportes.data.models.ConfigurationModel
import com.example.apptransportes.data.models.PointsModel
import com.example.apptransportes.data.models.RoutesModel

interface MainDataSource {
    //Getters
    suspend fun getCompanies(): List<CompaniesModel?>
    suspend fun getRoutes(companyId: String): List<RoutesModel?>
    suspend fun getPoints(companyId: String, routeId: String): List<PointsModel?>

    //Setters
    suspend fun setConfiguration(configuration: ConfigurationModel): String
}