package com.nishitnagar.simplibalance.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerBalanceDao {
    @Insert
    suspend fun insert(playerBalanceEntity: PlayerBalanceEntity)

    @Update
    suspend fun update(playerBalanceEntity: PlayerBalanceEntity)

    @Delete
    suspend fun delete(playerBalanceEntity: PlayerBalanceEntity)

    @Query("DELETE FROM Player_Balances")
    suspend fun deleteAll()

    @Query("SELECT * FROM Player_Balances")
    fun getPlayerBalancesFlow(): Flow<List<PlayerBalanceEntity>>

    @Query("SELECT * FROM Player_Balances")
    suspend fun getPlayerBalancesList(): List<PlayerBalanceEntity>
}