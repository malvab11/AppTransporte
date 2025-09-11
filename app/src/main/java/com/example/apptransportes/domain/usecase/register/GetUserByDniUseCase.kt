package com.example.apptransportes.domain.usecase.register

import com.example.apptransportes.domain.repository.RegisterRepository
import javax.inject.Inject

class GetUserByDniUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(dni: String) = repository.getUserByDni(dni = dni)
}