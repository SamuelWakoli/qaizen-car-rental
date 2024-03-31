package com.qaizen.admin.bookings.di

import com.qaizen.admin.bookings.data.repositories.QaizenBookingRepository
import com.qaizen.admin.bookings.domain.repositories.BookingsRepository
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
    fun providesQaizenBookingRepository(): BookingsRepository = QaizenBookingRepository()
}