package com.example.githubuser.viewModel

import android.app.Application
import android.app.DownloadManager.Query
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.database.FavoritUser
import com.example.githubuser.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): ViewModel() {

    private val _detail = MutableLiveData<DetailUserResponse?>()
    val detail: LiveData<DetailUserResponse?> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String?>()
    val userName: LiveData<String?> = _username

    val isFailedLoading: MutableLiveData<String> = MutableLiveData()

    fun findDetailUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detail.value = responseBody
                    }
                } else {
                    isFailedLoading.value = "Detail not Available"
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                isFailedLoading.value = "Failed to find detail"
            }
        })
    }

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getFavoriteUserByUsername(username: String): LiveData<FavoritUser> =
        mFavoriteRepository.getFavoriteUserByUsername(username)

    fun insert(favoritUser: FavoritUser) {
        mFavoriteRepository.insert(favoritUser)
    }

    fun delete(favoritUser: FavoritUser) {
        mFavoriteRepository.delete(favoritUser)
    }

    companion object {
        private const val TAG = "DetailUserViewModel"
        var Query = ""
    }
}