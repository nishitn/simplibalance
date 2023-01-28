package com.nishitnagar.simplibalance.repositories

import com.nishitnagar.simplibalance.data.ValueRatioDao
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import kotlinx.coroutines.flow.Flow

class ValueRatioRepository(
    private val valueRatioDao: ValueRatioDao
) {
    fun getValueRatios(): Flow<List<ValueRatioEntity>> = valueRatioDao.getValueRatio()

    suspend fun insert(valueRatioEntity: ValueRatioEntity) {
        valueRatioDao.deleteAll()
        valueRatioDao.insert(valueRatioEntity)
    }
}