package com.qaizen.admin.auth.di

import com.qaizen.admin.auth.data.repositories.QaizenAuthRepository
import com.qaizen.admin.auth.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return QaizenAuthRepository()
    }
}