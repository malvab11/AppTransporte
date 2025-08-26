package com.example.apptransportes.di.repository

import com.example.apptransportes.data.datasources.auth.AuthDataSource
import com.example.apptransportes.data.repository.AuthRepositoryImpl
import com.example.apptransportes.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthRepository(remoteDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(remoteDataSource = remoteDataSource)
    }
}