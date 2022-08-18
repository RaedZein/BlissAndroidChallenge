package com.raedzein.blisschallenge.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raedzein.blisschallenge.domain.model.GithubRepo

/**
 * @author Raed Zein
 * created on Thursday, 18 August, 2022
 */
@Dao
interface GithubRepoDao {
    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     */
    @Query("SELECT * FROM GithubRepo")
    fun allGithubRepos(): PagingSource<Int, GithubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: List<GithubRepo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: GithubRepo)

    @Query("DELETE FROM GithubRepo")
    suspend fun deleteAll()
}