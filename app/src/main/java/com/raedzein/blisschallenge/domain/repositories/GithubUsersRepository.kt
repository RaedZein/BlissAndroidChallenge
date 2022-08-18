package com.raedzein.blisschallenge.domain.repositories

import androidx.lifecycle.LiveData
import com.raedzein.blisschallenge.domain.ApiResultState
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.EmojiListResponse
import com.raedzein.blisschallenge.domain.model.GithubUser


interface GithubUsersRepository {

    suspend fun getGithubUsersFromDb(): List<GithubUser>
    suspend fun fetchGithubUserFromApi(userName:String): ApiResultState<GithubUser>
    suspend fun saveGithubUserToDb(user: GithubUser)
    fun getGithubUsersFromDbLiveData(): LiveData<List<GithubUser>>
    suspend fun deleteGithubUserFromDb(user: GithubUser)
}