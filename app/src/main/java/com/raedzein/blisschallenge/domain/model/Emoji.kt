package com.raedzein.blisschallenge.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
data class EmojiListResponse(
    val emojis: List<Emoji> = arrayListOf()
)

@Entity
data class Emoji(
    @PrimaryKey(autoGenerate = false)
    val name: String = "",
    val url: String = ""
)
