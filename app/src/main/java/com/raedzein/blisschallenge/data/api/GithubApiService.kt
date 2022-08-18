package com.raedzein.blisschallenge.data.api

import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.EmojiListResponse
import retrofit2.Response
import retrofit2.http.GET


interface GithubApiService {

    @GET("emojis")
    suspend fun getEmojis(): Response<EmojiListResponse>

}
