package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.GithubUserAdapter
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.viewModel.FollowViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val FollowViewModel by viewModels<FollowViewModel>()

    private var position: Int = 0
    private var userName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            userName = it.getString(ARG_USERNAME) ?: ""
        }

        if (position == 1) {
            FollowViewModel.dataFetch(ApiConfig.getApiService().getFollowers(userName))
            FollowViewModel.fetchData.observe(viewLifecycleOwner) { fetchData ->
                showFollowRecycle(fetchData)
            }
        } else {
            FollowViewModel.dataFetch(ApiConfig.getApiService().getFollowing(userName))
            FollowViewModel.fetchData.observe(viewLifecycleOwner) { fetchData ->
                showFollowRecycle(fetchData)
            }
        }

        FollowViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        FollowViewModel.isFailedLoading.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFollowRecycle(items: List<ItemsItem>?) {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = GithubUserAdapter()
        binding.rvFollow.adapter = adapter

        items?.let {
            adapter.submitList(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFollow.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}