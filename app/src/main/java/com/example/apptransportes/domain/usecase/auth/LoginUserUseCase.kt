package com.example.apptransportes.domain.usecase.auth

import com.example.apptransportes.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(dni: String, password: String) =
        repository.login(dni = dni, password = password)
}