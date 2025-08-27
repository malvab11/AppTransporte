package com.example.apptransportes.domain.usecase.home

import com.example.apptransportes.domain.repository.MainRepository
import javax.inject.Inject

class GetRoutesByCompanyUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(companyId: String) = repository.getRoutes(
        companyId = companyId
    )
}