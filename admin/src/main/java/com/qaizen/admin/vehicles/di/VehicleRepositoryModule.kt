package com.qaizen.admin.vehicles.di

import com.qaizen.admin.vehicles.data.repository.QaizenVehicleRepository
import com.qaizen.admin.vehicles.domain.repository.VehiclesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VehicleRepositoryModule {

    @Provides
    @Singleton
    fun provideVehicleRepository(): VehiclesRepository = QaizenVehicleRepository()
}