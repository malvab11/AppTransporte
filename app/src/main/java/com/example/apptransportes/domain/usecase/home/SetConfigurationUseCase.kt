package com.example.apptransportes.domain.usecase.home

import com.example.apptransportes.domain.entity.ConfigurationEntity
import com.example.apptransportes.domain.repository.MainRepository
import javax.inject.Inject

class SetConfigurationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(configuration: ConfigurationEntity) =
        repository.setConfiguration(configuration = configuration)
}