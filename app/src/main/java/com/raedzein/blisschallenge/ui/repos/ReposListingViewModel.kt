package com.raedzein.blisschallenge.ui.repos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.raedzein.blisschallenge.data.api.GitReposQueryBuilder
import com.raedzein.blisschallenge.data.source.RepositoriesMediator
import com.raedzein.blisschallenge.domain.repositories.GithubReposRepository
import com.raedzein.blisschallenge.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReposListingViewModel
@Inject constructor(private val repository: GithubReposRepository,
    //Query builder to be used for api calls (can be injectable in the future - for customized builder in unit tests)
                    queryBuilder: GitReposQueryBuilder
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val reposListingLiveData =
        Pager(
            config = PagingConfig(Constants.DEFAULT_PER_PAGE, enablePlaceholders = false),
            remoteMediator = RepositoriesMediator(queryBuilder,repository),
        ) {
            repository.getReposFromDb()
        }.flow.cachedIn(viewModelScope)
            .asLiveData()

}
