package com.raedzein.blisschallenge.application.di

import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import com.raedzein.blisschallenge.data.repositories.EmojisRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesRepository(repositoryImpl: EmojisRepositoryImpl): EmojisRepository
}