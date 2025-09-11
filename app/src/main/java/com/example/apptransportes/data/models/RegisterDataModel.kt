package com.example.apptransportes.data.models

data class RegisterDataModel(
    val uid: String = "",
    val users: UserModel? = null,
    val registerTime: Long? = System.currentTimeMillis()
)
