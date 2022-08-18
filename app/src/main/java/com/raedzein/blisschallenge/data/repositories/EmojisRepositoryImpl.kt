package com.raedzein.blisschallenge.data.repositories

import androidx.lifecycle.LiveData
import com.raedzein.blisschallenge.data.api.GithubApiService
import com.raedzein.blisschallenge.data.local.ApplicationDatabase
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.repositories.EmojisRepository
import javax.inject.Inject


class EmojisRepositoryImpl @Inject constructor(
    private val service: GithubApiService,
    private val database: ApplicationDatabase
):
    EmojisRepository {
    override suspend fun getEmojisFromDb() = database.emojiDao().getAll()
    override suspend fun saveEmojisToDb(data: List<Emoji>) = database.emojiDao().insert(data)
    override fun getEmojisFromDbLiveData(): LiveData<List<Emoji>> = database.emojiDao().getAllLiveData()

    override suspend fun deleteEmojiFromDb(emoji: Emoji) = database.emojiDao().delete(emoji.name)

    override suspend fun fetchEmojisFromApi() = safeApiResult { service.getEmojis() }

}