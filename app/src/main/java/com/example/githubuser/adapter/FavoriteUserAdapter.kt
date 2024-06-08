package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.RecycleItemBinding
import com.example.githubuser.ui.DetailUserActivity

class FavoriteUserAdapter(private val listUser: List<ItemsItem>?):
    ListAdapter<ItemsItem, FavoriteUserAdapter.FavoriteUserViewHolder>(DIFF_CALLBACK){

    class FavoriteUserViewHolder(val binding: RecycleItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.civItemPhoto)
            binding.tvUsername.text = user.login
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteUserViewHolder {
        val binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteUserAdapter.FavoriteUserViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intentFavorit = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentFavorit.putExtra("key_user", listUser?.get(holder.adapterPosition)?.login)
            holder.itemView.context.startActivity(intentFavorit)
        }
    }

    companion object {
        val DIFF_CALLBACK =  object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}