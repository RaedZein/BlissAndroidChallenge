package com.raedzein.blisschallenge.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUser(
    @PrimaryKey(autoGenerate = false)
    @SerializedName( "id") val userId: Long = 0,
    @SerializedName( "login") val username: String? = null,
    @SerializedName( "avatar_url") val avatarUrl: String? = null
):Parcelable
