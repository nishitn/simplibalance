package com.nishitnagar.simplibalance.di

import android.app.Application
import com.nishitnagar.simplibalance.data.AppDatabase
import com.nishitnagar.simplibalance.data.PlayerBalanceDao
import com.nishitnagar.simplibalance.data.ValueRatioDao
import com.nishitnagar.simplibalance.repositories.BalanceRepository
import com.nishitnagar.simplibalance.repositories.SettlementRepository
import com.nishitnagar.simplibalance.repositories.ValueRatioRepository
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
    fun provideValueRatioRepository(valueRatioDao: ValueRatioDao): ValueRatioRepository {
        return ValueRatioRepository(valueRatioDao)
    }

    @Singleton
    @Provides
    fun provideValueRatioDao(appDatabase: AppDatabase): ValueRatioDao {
        return appDatabase.valueRatioDao()
    }

    @Singleton
    @Provides
    fun provideSettlementRepository(
        balanceRepository: BalanceRepository, valueRatioRepository: ValueRatioRepository
    ): SettlementRepository {
        return SettlementRepository(balanceRepository, valueRatioRepository)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }
}