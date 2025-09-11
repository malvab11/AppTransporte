package com.example.apptransportes.domain.usecase.register

import com.example.apptransportes.domain.repository.RegisterRepository
import javax.inject.Inject

class GetRegisterTypesUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke() = repository.getRegisterType()
}