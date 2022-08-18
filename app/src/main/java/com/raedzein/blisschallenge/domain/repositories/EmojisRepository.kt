package com.raedzein.blisschallenge.domain.repositories

import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.EmojiListResponse


interface EmojisRepository {

    suspend fun getEmojisFromDb(): List<Emoji>
    suspend fun fetchEmojisFromApi(): ApiResultState<EmojiListResponse>
    suspend fun saveEmojisToDb(data: List<Emoji>)
}