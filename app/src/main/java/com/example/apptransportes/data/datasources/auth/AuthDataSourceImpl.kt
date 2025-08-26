package com.example.apptransportes.data.datasources.auth

import com.example.apptransportes.data.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : AuthDataSource {
    override suspend fun login(dni: String, password: String): UserModel? {
        // 1.- Revisar que exista el documento que es el DNI
        val loginDoc = fireStore.collection("logins").document(dni).get().await()
        if (!loginDoc.exists()) return null
        val passwordHash = loginDoc.getString("password") ?: return null

        //2.- Comparar el passwrodHash con el password Ingresado
        val loginSucces = passwordHash == password
        if (!loginSucces) return null

        //3.- Si es success, continuar con la obtencion de la info
        val user = fireStore.collection("users").document(dni).get().await()
        return user.toObject(UserModel::class.java)
    }
}