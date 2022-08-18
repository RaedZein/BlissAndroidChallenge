package com.raedzein.blisschallenge.data.repositories

import com.raedzein.blisschallenge.data.api.GithubApiService
import com.raedzein.blisschallenge.data.local.ApplicationDatabase
import com.raedzein.blisschallenge.domain.model.GithubRepo
import com.raedzein.blisschallenge.domain.repositories.GithubReposRepository
import javax.inject.Inject


class GithubReposRepositoryImpl @Inject constructor(
    private val service: GithubApiService,
    private val database: ApplicationDatabase
): GithubReposRepository {

    override suspend fun fetchGithubReposFromApi(
        userName: String,
        query: Map<String, String>
    ) = safeApiResult { service.getRepos(userName, query) }

    override fun getReposFromDb() =
        database.githubReposDao().allGithubRepos()

    override suspend fun saveGithubReposToDb(repos: List<GithubRepo>) =
        database.githubReposDao().insert(repos)

}