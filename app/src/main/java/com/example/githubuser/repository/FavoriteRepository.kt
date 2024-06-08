package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoritUser
import com.example.githubuser.database.FavoritUserDao
import com.example.githubuser.database.FavoritUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteUserDao: FavoritUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoritUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoritUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavoritUser>> = mFavoriteUserDao.getAllFavoritUser()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoritUser> =
        mFavoriteUserDao.getFavoriteUserByUsername(username)

    fun insert(favoritUser: FavoritUser) {
        executorService.execute { mFavoriteUserDao.insert(favoritUser) }
    }

    fun delete(favoritUser: FavoritUser) {
        executorService.execute { mFavoriteUserDao.delete(favoritUser) }
    }
}