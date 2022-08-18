package com.raedzein.blisschallenge.application.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.raedzein.blisschallenge.BuildConfig
import com.raedzein.blisschallenge.data.api.GitReposQueryBuilder
import com.raedzein.blisschallenge.data.api.GithubApiService
import com.raedzein.blisschallenge.data.api.GoogleReposQueryBuilder
import com.raedzein.blisschallenge.data.deserializer.EmojiTypeAdapter
import com.raedzein.blisschallenge.domain.model.Emoji
import com.raedzein.blisschallenge.domain.model.EmojiListResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteApiModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(appContext).build())
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .retryOnConnectionFailure(true)
            .build()

    }

    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext appContext: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(providesOkHttpClient(appContext))
            .addConverterFactory(GsonConverterFactory.create(createGsonInstance()))
            .build()
    }
    private fun createGsonInstance(): Gson = GsonBuilder()
        .registerTypeAdapter(EmojiListResponse::class.java, EmojiTypeAdapter())
//        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun providesGitReposeService(@ApplicationContext appContext: Context): GithubApiService {
        return providesRetrofit(appContext).create(GithubApiService::class.java)
    }

    @Provides
    fun providesGoogleReposQueryBuilder():GitReposQueryBuilder = GoogleReposQueryBuilder()
}