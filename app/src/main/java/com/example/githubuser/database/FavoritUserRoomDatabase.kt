package com.example.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class FavoritUserRoomDatabase {

    @Database(entities = [FavoritUser::class], version = 1)
    abstract class FavoritUserRoomDatabase : RoomDatabase() {
        abstract fun favoritUserDao(): FavoritUserDao

        companion object {
            @Volatile
            private var INSTANCE: FavoritUserRoomDatabase? = null

            @JvmStatic
            fun getDatabase(context: Context): FavoritUserRoomDatabase {
                if (INSTANCE == null) {
                    synchronized(FavoritUserRoomDatabase::class.java) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavoritUserRoomDatabase::class.java, "favoritUser_database"
                        )
                            .build()
                    }
                }
                return INSTANCE as FavoritUserRoomDatabase
            }
        }
    }
}