package com.example.apptransportes.di.datasources

import com.example.apptransportes.data.datasources.AuthDataSource
import com.example.apptransportes.data.remote.firebase.AuthDataSourceImpl
import com.example.apptransportes.data.datasources.MainDataSource
import com.example.apptransportes.data.remote.firebase.MainDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {

    @Provides
    @Singleton
    fun providesAuthDataSource(firebaseFirestore: FirebaseFirestore): AuthDataSource {
        return AuthDataSourceImpl(firebaseFirestore = firebaseFirestore)
    }

    @Provides
    @Singleton
    fun providesMainDataSource(firebaseFirestore: FirebaseFirestore): MainDataSource {
        return MainDataSourceImpl(firebaseFirestore = firebaseFirestore)
    }
}