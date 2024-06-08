package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.database.FavoritUser

class FavoriteDiffCallback(private val oldFavoriteList: List<FavoritUser>, private val newFavoriteList: List<FavoritUser>): DiffUtil.Callback() {
    override fun getOldListSize(): Int =
        oldFavoriteList.size

    override fun getNewListSize(): Int =
        newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoritUser = oldFavoriteList[oldItemPosition]
        val newFavoritUser = newFavoriteList[newItemPosition]
        return oldFavoritUser.username == newFavoritUser.username && oldFavoritUser.avatarUrl == newFavoritUser.avatarUrl
    }
}