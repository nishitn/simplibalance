package com.nishitnagar.simplibalance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


@Database(entities = [PlayerBalanceEntity::class, ValueRatioEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerBalanceDao(): PlayerBalanceDao

    abstract fun valueRatioDao(): ValueRatioDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                val rdc: Callback = object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadScheduledExecutor().execute {
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).valueRatioDao().insert(ValueRatioEntity())
                            }
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        // do something every time database is open
                    }
                }

                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "simplibalance.db")
                    .fallbackToDestructiveMigration().addCallback(rdc).build()
            }
            return INSTANCE as AppDatabase
        }
    }
}