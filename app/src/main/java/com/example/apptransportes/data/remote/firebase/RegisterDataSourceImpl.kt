package com.example.apptransportes.data.remote.firebase

import com.example.apptransportes.data.datasources.RegisterDataSource
import com.example.apptransportes.data.models.RegisterDataModel
import com.example.apptransportes.data.models.RegisterTypeModel
import com.example.apptransportes.data.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : RegisterDataSource {
    override suspend fun getRegisterType(): List<RegisterTypeModel?> {
        val registerTypesDocs = firebaseFirestore.collection("register_type").get().await()
        if (registerTypesDocs.isEmpty) return emptyList()
        val registerTypes = registerTypesDocs.documents.map {
            it.toObject(RegisterTypeModel::class.java)?.copy(uid = it.id)
        }
        return registerTypes
    }

    override suspend fun getUserByDni(dni: String): UserModel? {
        val user = firebaseFirestore.collection("users").whereEqualTo("dni", dni).get()
            .await().documents.firstOrNull() ?: return null
        return user.toObject(UserModel::class.java)
    }

    override suspend fun registerInfo(data: List<RegisterDataModel>): List<String> {
        return data.map { register ->
            try {
                val docRef = firebaseFirestore.collection("registers").add(register).await()
                "Se registro con existo la información de ${register.users!!.nombres}"
            } catch (e: Exception) {
                e.message ?: "Ocurrio un Error en el registro de ${register.users!!.nombres}"
            }
        }
    }

}