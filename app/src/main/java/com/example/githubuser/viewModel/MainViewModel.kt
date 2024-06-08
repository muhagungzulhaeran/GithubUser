package com.example.githubuser.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.GithubResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<List<ItemsItem>>()
    val items: LiveData<List<ItemsItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val isFailedLoading: MutableLiveData<String> = MutableLiveData()

    init {
        findUsers("q")
    }

    fun userSearch(query: String) {
        if (query.isNotBlank()) {
            findUsers(query)
        }
    }

    private fun findUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username ?: "username")
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = responseBody.items
                    }
                } else {
                    isFailedLoading.value = "User Tidak Ditemukan"
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                isFailedLoading.value = "Gagal mengambil data: ${t.message}"
            }
        })
    }
}