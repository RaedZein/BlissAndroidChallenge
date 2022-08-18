package com.raedzein.blisschallenge.domain.repositories

import androidx.paging.PagingSource
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.model.GithubRepo


interface GithubReposRepository {

    suspend fun fetchGithubReposFromApi(userName: String,
                                        query: Map<String, String>): ApiResultState<List<GithubRepo>>
    suspend fun saveGithubReposToDb(repos: List<GithubRepo>)
    fun getReposFromDb(): PagingSource<Int, GithubRepo>
}