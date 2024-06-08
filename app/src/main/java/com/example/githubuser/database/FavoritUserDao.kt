package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoritUser: FavoritUser)

    @Delete
    fun delete(favoritUser: FavoritUser)

    @Query("SELECT * FROM favorituser ORDER BY username ASC")
    fun getAllFavoritUser(): LiveData<List<FavoritUser>>

    @Query("SELECT * FROM favorituser WHERE username = username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoritUser>
}