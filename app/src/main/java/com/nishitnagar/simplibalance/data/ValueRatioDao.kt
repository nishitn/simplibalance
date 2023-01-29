package com.nishitnagar.simplibalance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ValueRatioDao {
    @Insert
    suspend fun insert(valueRatioEntity: ValueRatioEntity)

    @Update
    suspend fun update(valueRatioEntity: ValueRatioEntity)

    @Query("SELECT * FROM Value_Ratio LIMIT 1")
    suspend fun getValueRatioList(): List<ValueRatioEntity>

    @Query("SELECT * FROM Value_Ratio LIMIT 1")
    fun getValueRatioFlow(): Flow<List<ValueRatioEntity>>
}