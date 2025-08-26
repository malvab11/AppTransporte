package com.example.apptransportes.di.datasources

import com.example.apptransportes.data.datasources.auth.AuthDataSource
import com.example.apptransportes.data.datasources.auth.AuthDataSourceImpl
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
    fun providesAuthDataSource(firebaseFirestore: FirebaseFirestore): AuthDataSource{
        return AuthDataSourceImpl(fireStore = firebaseFirestore)
    }
}