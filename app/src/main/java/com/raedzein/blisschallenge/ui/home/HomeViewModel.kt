package com.raedzein.blisschallenge.ui.home

import androidx.lifecycle.*
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import com.raedzein.blisschallenge.ui.base.ViewLoaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject constructor(private val repository: EmojisRepository) : ViewModel() {

    private val _randomEmojiLiveData: MutableLiveData<RandomEmojiState> = MutableLiveData()
    val randomEmojiLiveData: LiveData<RandomEmojiState> = _randomEmojiLiveData

    init {
        viewModelScope.launch(Dispatchers.Main) {
            val emojisInDb = repository.getEmojisFromDb()
            if(emojisInDb.isEmpty())
                _randomEmojiLiveData.value = ViewLoaderState.Init
            else {
                val randomEmoji = emojisInDb.random()
                _randomEmojiLiveData.value = ViewLoaderState.Loaded(randomEmoji)
            }
        }
    }

    fun getRandomEmoji() {
        viewModelScope.launch(Dispatchers.Main) {
            val emojisInDb = repository.getEmojisFromDb()
            if(emojisInDb.isEmpty()){
                _randomEmojiLiveData.value = ViewLoaderState.Loading

                when (val result = repository.fetchEmojisFromApi()) {
                    is ApiResultState.Success ->{
                        repository.saveEmojisToDb(result.data.emojis)
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


}

