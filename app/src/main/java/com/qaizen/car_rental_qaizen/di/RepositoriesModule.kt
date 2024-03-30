package com.qaizen.car_rental_qaizen.di

import com.qaizen.car_rental_qaizen.data.repositories.QaizenAuthRepository
import com.qaizen.car_rental_qaizen.data.repositories.QaizenProfileRepository
import com.qaizen.car_rental_qaizen.data.repositories.QaizenVehiclesRepository
import com.qaizen.car_rental_qaizen.domain.repositories.AuthRepository
import com.qaizen.car_rental_qaizen.domain.repositories.ProfileRepository
import com.qaizen.car_rental_qaizen.domain.repositories.VehiclesRepository
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

    @Provides
    @Singleton
    fun provideVehiclesRepository(): VehiclesRepository {
        return QaizenVehiclesRepository()
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(): ProfileRepository {
        return QaizenProfileRepository()
    }
}