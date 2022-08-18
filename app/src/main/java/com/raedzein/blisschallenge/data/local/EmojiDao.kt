package com.raedzein.blisschallenge.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raedzein.blisschallenge.domain.model.Emoji

/**
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
@Dao
interface EmojiDao {
    @Query("SELECT * FROM Emoji")
    fun getAllLiveData(): LiveData<List<Emoji>>

    @Query("SELECT * FROM Emoji")
    suspend fun getAll(): List<Emoji>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: List<Emoji>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: Emoji)

    @Query("DELETE FROM Emoji")
    suspend fun deleteAll()
}