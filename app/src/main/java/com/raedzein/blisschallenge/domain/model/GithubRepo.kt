package com.raedzein.blisschallenge.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
@Entity
@Parcelize
data class GithubRepo(
    @PrimaryKey(autoGenerate = false) @SerializedName( "id") val id: Long = 0,
    @SerializedName( "name") val name: String? = null,
    @SerializedName( "language") val language: String? = null,
    @SerializedName( "description") val description: String? = null,
    @SerializedName( "stargazers_count") val starsCount: Int? = null,
    @SerializedName( "forks_count") val forks: Int = 0,
    @Embedded @SerializedName( "owner") val owner: GithubUser = GithubUser(),
):Parcelable
