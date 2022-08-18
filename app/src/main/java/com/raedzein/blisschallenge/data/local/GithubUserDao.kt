package com.raedzein.blisschallenge.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raedzein.blisschallenge.domain.model.GithubUser

/**
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
@Dao
interface GithubUserDao {

    @Query("SELECT * FROM GithubUser")
    fun getAllLiveData(): LiveData<List<GithubUser>>

    @Query("SELECT * FROM GithubUser")
    suspend fun getAll(): List<GithubUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: GithubUser)

    @Query("DELETE FROM GithubUser")
    suspend fun deleteAll()

    @Query("DELETE FROM GithubUser where userId = :id")
    suspend fun delete(id: Long)
}