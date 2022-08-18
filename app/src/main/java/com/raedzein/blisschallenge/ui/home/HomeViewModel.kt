package com.raedzein.blisschallenge.ui.home

import androidx.lifecycle.*
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import com.raedzein.blisschallenge.domain.repositories.GithubUsersRepository
import com.raedzein.blisschallenge.ui.base.ViewLoaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject constructor(private val emojisRepository: EmojisRepository,
                    private val githubUserRepository: GithubUsersRepository) : ViewModel() {

    private val _randomEmojiLiveData: MutableLiveData<RandomEmojiState> = MutableLiveData()
    val randomEmojiLiveData: LiveData<RandomEmojiState> = _randomEmojiLiveData

    init {
        viewModelScope.launch(Dispatchers.Main) {
            val emojisInDb = emojisRepository.getEmojisFromDb()
            if(emojisInDb.isEmpty())
                _randomEmojiLiveData.value = ViewLoaderState.Init
            else {
                val randomEmoji = emojisInDb.random()
                _randomEmojiLiveData.value = ViewLoaderState.Loaded(randomEmoji)
            }

            _githubUserLiveData.value = ViewLoaderState.Init
        }
    }

    fun getRandomEmoji() {
        viewModelScope.launch(Dispatchers.Main) {
            val emojisInDb = emojisRepository.getEmojisFromDb()
            if(emojisInDb.isEmpty()){
                _randomEmojiLiveData.value = ViewLoaderState.Loading

                when (val result = emojisRepository.fetchEmojisFromApi()) {
                    is ApiResultState.Success ->{
                        emojisRepository.saveEmojisToDb(result.data.emojis)
                        val randomEmoji = result.data.emojis.random()
                        _randomEmojiLiveData.value = ViewLoaderState.Loaded(randomEmoji)
                    }
                    is ApiResultState.Error ->
                        _randomEmojiLiveData.value = ViewLoaderState.Failed(result.exception)
                }
            } else {
                val randomEmoji = emojisInDb.random()
                _randomEmojiLiveData.value = ViewLoaderState.Loaded(randomEmoji)
            }
        }
    }
    private val _githubUserLiveData: MutableLiveData<GithubUserState> = MutableLiveData()
    val githubUserLiveData: LiveData<GithubUserState> = _githubUserLiveData

    private var searchUsername = ""
    fun setUserNameText(queryText: String) {
        searchUsername = queryText
    }
    fun getUserNameText() = searchUsername

    fun searchForGithubUser() {
        viewModelScope.launch(Dispatchers.Main) {
            _githubUserLiveData.value = ViewLoaderState.Loading

            when (val result = githubUserRepository.fetchGithubUserFromApi(searchUsername)) {
                is ApiResultState.Success ->{
                    githubUserRepository.saveGithubUserToDb(result.data)
                    _githubUserLiveData.value = ViewLoaderState.Loaded(result.data)
                }
                is ApiResultState.Error ->
                    _githubUserLiveData.value = ViewLoaderState.Failed(result.exception)
            }
        }
    }



}

