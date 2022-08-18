package com.raedzein.blisschallenge.ui.avatars

import androidx.lifecycle.*
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.GithubUser
import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import com.raedzein.blisschallenge.domain.repositories.GithubUsersRepository
import com.raedzein.blisschallenge.ui.base.ViewLoaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AvatarListViewModel
@Inject constructor(private val repository: GithubUsersRepository) : ViewModel() {

    val emojiLiveData: LiveData<List<GithubUser>> = repository.getGithubUsersFromDbLiveData()

    fun removeGithubUserFromDb(githubUser: GithubUser) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.deleteGithubUserFromDb(githubUser)
        }
    }


}

