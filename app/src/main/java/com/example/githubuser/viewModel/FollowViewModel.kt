package com.example.githubuser.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _fetchData = MutableLiveData<List<ItemsItem>?>()
    val fetchData: LiveData<List<ItemsItem>?> = _fetchData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val isFailedLoading: MutableLiveData<String> = MutableLiveData()

    fun dataFetch(client: Call<List<ItemsItem>>) {
        _isLoading.value = true
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val listData = response.body()
                    _fetchData.value = listData
                } else {
                    isFailedLoading.value = "List is empty"
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                isFailedLoading.value = "Failed to fetch data: ${t.message}"
            }
        })
    }
}