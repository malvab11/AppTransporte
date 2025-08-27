package com.example.apptransportes.di.repository

import com.example.apptransportes.data.datasources.AuthDataSource
import com.example.apptransportes.data.datasources.MainDataSource
import com.example.apptransportes.data.repository.AuthRepositoryImpl
import com.example.apptransportes.data.repository.MainRepositoryImpl
import com.example.apptransportes.domain.repository.AuthRepository
import com.example.apptransportes.domain.repository.MainRepository
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

    @Provides
    @Singleton
    fun providesMainRepository(mainDataSource: MainDataSource): MainRepository{
        return MainRepositoryImpl(mainDataSource = mainDataSource)
    }

}