package com.example.apptransportes.data.repository

import com.example.apptransportes.data.datasources.MainDataSource
import com.example.apptransportes.data.mappers.toModel
import com.example.apptransportes.data.mappers.toEntity
import com.example.apptransportes.domain.entity.CompaniesEntity
import com.example.apptransportes.domain.entity.ConfigurationEntity
import com.example.apptransportes.domain.entity.PointsEntity
import com.example.apptransportes.domain.entity.RoutesEntity
import com.example.apptransportes.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun getCompanies(): Result<List<CompaniesEntity>> {
        return try {
            val companies = mainDataSource.getCompanies()
            if (companies.isNotEmpty()) {
                Result.success(companies.map { it!!.toEntity() }.toList())
            } else {
                Result.failure(Exception("No existen registro de Compañias"))
            }
        } catch (e: Exception) {
            Result.failure<List<CompaniesEntity>>(e)
        }
    }

    override suspend fun getRoutes(companyId: String): Result<List<RoutesEntity>> {
        return try {
            val routes = mainDataSource.getRoutes(companyId = companyId)
            if (routes.isNotEmpty()) {
                Result.success(routes.map { it!!.toEntity() }.toList())
            } else {
                Result.failure(Exception("La compañia no tiene rutas registradas"))
            }
        } catch (e: Exception) {
            Result.failure<List<RoutesEntity>>(e)
        }
    }

    override suspend fun getPoints(companyId: String, routeId: String): Result<List<PointsEntity>> {
        return try {
            val points = mainDataSource.getPoints(
                companyId = companyId,
                routeId = routeId
            )
            if (points.isNotEmpty()) {
                Result.success(points.map { it!!.toEntity() }.toList())
            } else {
                Result.failure(Exception("No existen puntos asociados para la ruta seleccionada"))
            }
        } catch (e: Exception) {
            Result.failure<List<PointsEntity>>(e)
        }
    }

    override suspend fun setConfiguration(configuration: ConfigurationEntity): Result<String> {
        return try {
            val id = mainDataSource.setConfiguration(configuration = configuration.toModel())
            Result.success(id)
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
    }
}