package com.raedzein.blisschallenge.data.api

import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.EmojiListResponse
import com.raedzein.blisschallenge.domain.model.GithubRepo
import com.raedzein.blisschallenge.domain.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface GithubApiService {

    @GET("emojis")
    suspend fun getEmojis(): Response<EmojiListResponse>

    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): Response<GithubUser>

    @GET("users/{userName}/repos")
    suspend fun getRepos(@Path("userName") userName: String,
                         @QueryMap queries: Map<String,String>): Response<List<GithubRepo>>

}
