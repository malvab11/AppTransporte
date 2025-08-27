package com.example.apptransportes.data.remote.firebase

import com.example.apptransportes.data.datasources.MainDataSource
import com.example.apptransportes.data.models.CompaniesModel
import com.example.apptransportes.data.models.PointsModel
import com.example.apptransportes.data.models.RoutesModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : MainDataSource {
    override suspend fun getCompanies(): List<CompaniesModel?> {
        val companiesDocs = firebaseFirestore.collection("companies").get().await()
        if (companiesDocs.isEmpty) return emptyList()
        val companies = companiesDocs.documents.map { it.toObject(CompaniesModel::class.java)?.copy(empresaId = it.id) }
        return companies
    }

    override suspend fun getRoutes(companyId: String): List<RoutesModel?> {
        val routesDocs =
            firebaseFirestore.collection("routes").whereEqualTo("empresaId", companyId).get()
                .await()
        if (routesDocs.isEmpty) return emptyList()
        val routes = routesDocs.documents.map { it.toObject(RoutesModel::class.java)?.copy(routeId = it.id) }
        return routes
    }

    override suspend fun getPoints(companyId: String, routeId: String): List<PointsModel?> {
        val pointsDocs = firebaseFirestore.collection("points").whereEqualTo("empresaId", companyId)
            .whereEqualTo("rutaId", routeId).get().await()
        if (pointsDocs.isEmpty) return emptyList()
        val points = pointsDocs.documents.map { it.toObject(PointsModel::class.java)?.copy(pointId = it.id)  }
        return points
    }
}