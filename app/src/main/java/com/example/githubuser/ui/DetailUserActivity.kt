package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.viewModel.DetailUserViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.database.FavoritUser
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var favoritUser: FavoritUser? = null
    private val viewModel = DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@DetailUserActivity)

        val item = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(key_user)
        } else {
            @Suppress("DEPRECATION")
            intent.getStringExtra(key_user)
        }
        viewModel.findUser(item)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.userName = item
        val viewPager = binding.vp
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tl
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        viewModel.userName.observe(this) { userName ->
            setUserDetailData(userName)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoritUser = FavoritUser()

        viewModel.getfavoriteUserByUsername(dataUser.toString()).observe(this) { favoritUser ->
            val btnFav = binding.fabFavorit
            if (favoritUser == null) {
                btnFav.setBackgroundResource(R.drawable.ic)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun bindDetailUser(detail: DetailUserResponse) {
        with(binding) {
            Glide.with(root.context)
                .load(detail.avatarUrl)
                .into(imageDetail)
            tvNamaLengkap.text = detail.name
            tvUsername.text = detail.login
            tvFollower.text = "${detail.followers} Followers"
            tvFollowing.text = "${detail.following} Following"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val key_user = "key_user"
    }
}