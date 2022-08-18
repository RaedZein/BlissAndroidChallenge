package com.raedzein.blisschallenge.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GithubUser(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    @SerializedName( "login") val username: String? = null,
    @SerializedName( "avatar_url") val avatarUrl: String? = null
)
