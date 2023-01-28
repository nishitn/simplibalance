package com.nishitnagar.simplibalance.repositories

import com.nishitnagar.simplibalance.data.PlayerBalanceDao
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import kotlinx.coroutines.flow.Flow

class BalanceRepository(
    private val playerBalanceDao: PlayerBalanceDao
) {
    fun getPlayerBalancesFlow(): Flow<List<PlayerBalanceEntity>> = playerBalanceDao.getPlayerBalances()

    suspend fun insert(playerBalanceEntity: PlayerBalanceEntity) = playerBalanceDao.insert(playerBalanceEntity)

    suspend fun update(playerBalanceEntity: PlayerBalanceEntity) = playerBalanceDao.update(playerBalanceEntity)

    suspend fun delete(playerBalanceEntity: PlayerBalanceEntity) = playerBalanceDao.delete(playerBalanceEntity)

    suspend fun deleteAll() = playerBalanceDao.deleteAll()
}