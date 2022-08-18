package com.raedzein.blisschallenge.application.di

import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import com.raedzein.blisschallenge.data.repositories.EmojisRepositoryImpl
import com.raedzein.blisschallenge.data.repositories.GithubReposRepositoryImpl
import com.raedzein.blisschallenge.data.repositories.GithubUsersRepositoryImpl
import com.raedzein.blisschallenge.domain.repositories.GithubReposRepository
import com.raedzein.blisschallenge.domain.repositories.GithubUsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesEmojisRepository(repositoryImpl: EmojisRepositoryImpl): EmojisRepository
    @Binds
    abstract fun providesGithubUsersRepository(repositoryImpl: GithubUsersRepositoryImpl): GithubUsersRepository

    @Binds
    abstract fun providesGithubReposRepository(repositoryImpl: GithubReposRepositoryImpl): GithubReposRepository
}