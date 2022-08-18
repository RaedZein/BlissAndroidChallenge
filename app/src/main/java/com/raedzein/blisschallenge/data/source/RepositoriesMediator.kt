package com.raedzein.blisschallenge.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.raedzein.blisschallenge.data.api.GitReposQueryBuilder
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.model.GithubRepo
import com.raedzein.blisschallenge.domain.repositories.GithubReposRepository

@OptIn(ExperimentalPagingApi::class)
class RepositoriesMediator(
  private val queryBuilder: GitReposQueryBuilder,
  private val repository: GithubReposRepository
) : RemoteMediator<Int, GithubRepo>() {
  private var currentPage = 1
  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, GithubRepo>
  ): MediatorResult {
    currentPage = when (loadType) {
      LoadType.REFRESH -> 1

      LoadType.PREPEND ->
        return MediatorResult.Success(endOfPaginationReached = false)

      LoadType.APPEND -> currentPage.plus(1)
    }

    return when (val result = repository.fetchGithubReposFromApi(queryBuilder.getCurrentUser(),
      queryBuilder.build(currentPage))) {
      is ApiResultState.Success -> {

        val repos = result.data
        val endOfPaginationReached = repos.isEmpty()

        //Incase you need to clear the repos
//        if (loadType == LoadType.REFRESH)
//          repository.clearAllReposFromDb()

        repository.saveGithubReposToDb(repos)
        MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
      }
      is ApiResultState.Error ->
        MediatorResult.Error(result.exception)
    }
  }
  private fun getRefreshKey(state: PagingState<Int, GithubRepo>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}