package com.raedzein.blisschallenge.ui.emojis

import androidx.lifecycle.*
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import com.raedzein.blisschallenge.ui.base.ViewLoaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EmojisListViewModel
@Inject constructor(private val repository: EmojisRepository) : ViewModel() {

    val emojiLiveData: LiveData<List<Emoji>> = repository.getEmojisFromDbLiveData()
    private val _fetchEmojiLiveData: MutableLiveData<FetchEmojiState> = MutableLiveData()
    val fetchEmojiLiveData: LiveData<FetchEmojiState> = _fetchEmojiLiveData

    init {
        _fetchEmojiLiveData.value = ViewLoaderState.Init
    }

    fun refreshEmojisFromRemoteApi() {
        viewModelScope.launch(Dispatchers.Main) {
            _fetchEmojiLiveData.value = ViewLoaderState.Loading

            when (val result = repository.fetchEmojisFromApi()) {
                is ApiResultState.Success ->{
                    repository.saveEmojisToDb(result.data.emojis)
                    _fetchEmojiLiveData.value = ViewLoaderState.Loaded(null)
                }
                is ApiResultState.Error ->
                    _fetchEmojiLiveData.value = ViewLoaderState.Failed(result.exception)
            }
        }
    }

    fun removeEmojiFromDb(emoji: Emoji) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.deleteEmojiFromDb(emoji)
        }
    }


}

