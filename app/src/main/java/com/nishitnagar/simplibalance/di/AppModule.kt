package com.nishitnagar.simplibalance.di

import android.app.Application
import com.nishitnagar.simplibalance.data.AppDatabase
import com.nishitnagar.simplibalance.data.PlayerBalanceDao
import com.nishitnagar.simplibalance.repositories.BalanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideBalanceRepository(playerBalanceDao: PlayerBalanceDao): BalanceRepository {
        return BalanceRepository(playerBalanceDao)
    }

    @Singleton
    @Provides
    fun providePlayerBalanceDao(appDatabase: AppDatabase): PlayerBalanceDao {
        return appDatabase.playerBalanceDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }
}