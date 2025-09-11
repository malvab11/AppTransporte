package com.example.apptransportes.di.repository

import com.example.apptransportes.data.datasources.AuthDataSource
import com.example.apptransportes.data.datasources.MainDataSource
import com.example.apptransportes.data.datasources.RegisterDataSource
import com.example.apptransportes.data.repository.AuthRepositoryImpl
import com.example.apptransportes.data.repository.MainRepositoryImpl
import com.example.apptransportes.data.repository.RegisterRepositoryImpl
import com.example.apptransportes.domain.repository.AuthRepository
import com.example.apptransportes.domain.repository.MainRepository
import com.example.apptransportes.domain.repository.RegisterRepository
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

    @Provides
    @Singleton
    fun providerRegisterRepository(registerDataSource: RegisterDataSource): RegisterRepository {
        return RegisterRepositoryImpl(registerDataSource = registerDataSource)
    }

}