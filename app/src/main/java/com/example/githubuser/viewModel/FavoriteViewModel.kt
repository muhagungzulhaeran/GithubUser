package com.example.githubuser.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoritUser
import com.example.githubuser.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllfavorites(): LiveData<List<FavoritUser>> =  mFavoriteRepository.getAllFavorites()
}