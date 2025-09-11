package com.example.apptransportes.data.remote.firebase

import com.example.apptransportes.data.datasources.AuthDataSource
import com.example.apptransportes.data.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : AuthDataSource {
    override suspend fun login(dni: String, password: String): UserModel? {
        // 1.- Revisar que exista el documento que es el DNI
        val login = firebaseFirestore.collection("logins").whereEqualTo("dni",dni).whereEqualTo("password",password).get().await()
        if (login.isEmpty) return null

        //2.- Comparar el passwrodHash con el password Ingresado
        val user = firebaseFirestore.collection("users").whereEqualTo("dni",dni).get().await().documents.firstOrNull() ?: return null
        return user.toObject(UserModel::class.java)
    }


}