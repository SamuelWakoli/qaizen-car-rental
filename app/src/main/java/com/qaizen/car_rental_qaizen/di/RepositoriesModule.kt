package com.qaizen.car_rental_qaizen.di

import com.qaizen.car_rental_qaizen.data.repositories.QaizenAuthRepository
import com.qaizen.car_rental_qaizen.domain.repositories.AuthRepository
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