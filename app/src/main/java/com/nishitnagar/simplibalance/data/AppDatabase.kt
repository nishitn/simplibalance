package com.nishitnagar.simplibalance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayerBalanceEntity::class, ValueRatioEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerBalanceDao(): PlayerBalanceDao

    abstract fun valueRatioDao(): ValueRatioDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "simplibalance.db")
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE as AppDatabase
        }
    }
}