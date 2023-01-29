package com.nishitnagar.simplibalance.repositories

import com.nishitnagar.simplibalance.data.ValueRatioDao
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import kotlinx.coroutines.flow.Flow

class ValueRatioRepository(
    private val valueRatioDao: ValueRatioDao
) {
    fun getValueRatiosFlow(): Flow<List<ValueRatioEntity>> = valueRatioDao.getValueRatioFlow()

    suspend fun getValueRatiosList(): List<ValueRatioEntity> = valueRatioDao.getValueRatioList()

    suspend fun update(valueRatioEntity: ValueRatioEntity) {
        valueRatioDao.update(valueRatioEntity)
    }
}