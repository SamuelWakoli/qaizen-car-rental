package com.qaizen.admin.users.di

import com.qaizen.admin.users.data.QaizenUsersRepository
import com.qaizen.admin.users.domain.repository.UsersRepository
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
    fun provideUsersRepository(): UsersRepository = QaizenUsersRepository()
}