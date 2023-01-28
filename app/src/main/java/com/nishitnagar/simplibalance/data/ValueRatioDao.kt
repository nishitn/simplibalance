package com.nishitnagar.simplibalance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ValueRatioDao {
    @Insert
    suspend fun insert(valueRatioEntity: ValueRatioEntity)

    @Query("DELETE FROM Value_Ratio")
    suspend fun deleteAll()

    @Query("SELECT * FROM Value_Ratio LIMIT 1")
    fun getValueRatio(): Flow<List<ValueRatioEntity>>
}