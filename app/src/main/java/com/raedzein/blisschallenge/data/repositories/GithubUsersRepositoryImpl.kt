package com.raedzein.blisschallenge.data.repositories

import com.raedzein.blisschallenge.data.api.GithubApiService
import com.raedzein.blisschallenge.data.local.ApplicationDatabase
import com.raedzein.blisschallenge.domain.model.GithubUser
import com.raedzein.blisschallenge.domain.repositories.GithubUsersRepository
import javax.inject.Inject


class GithubUsersRepositoryImpl @Inject constructor(
    private val service: GithubApiService,
    private val database: ApplicationDatabase
): GithubUsersRepository {

    override suspend fun getGithubUsersFromDb() = database.githubUserDao().getAll()

    override suspend fun fetchGithubUserFromApi(userName:String) = safeApiResult { service.getUser(userName) }

    override suspend fun saveGithubUserToDb(user: GithubUser) =
        database.githubUserDao().insert(user)

    override fun getGithubUsersFromDbLiveData() = database.githubUserDao().getAllLiveData()

    override suspend fun deleteGithubUserFromDb(user: GithubUser) = database.githubUserDao().delete(user.userId)
}