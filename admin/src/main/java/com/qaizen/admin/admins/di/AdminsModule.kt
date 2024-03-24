package com.qaizen.admin.admins.di

import com.qaizen.admin.admins.data.AdminRepository
import com.qaizen.admin.admins.data.QaizenAdminRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdminsModule {

    @Provides
    @Singleton
    fun provideAdminRepository(): AdminRepository = QaizenAdminRepository()
}