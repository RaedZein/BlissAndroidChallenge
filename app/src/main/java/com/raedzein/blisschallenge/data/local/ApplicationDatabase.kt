package com.raedzein.blisschallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raedzein.blisschallenge.domain.model.Emoji

/**
 * The Room database that contains the GithubRepo table
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
@Database(entities = [Emoji::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun emojiDao(): EmojiDao

    companion object {

        @Volatile private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ApplicationDatabase::class.java, "androidchallenge.db")
                .build()
    }
}

