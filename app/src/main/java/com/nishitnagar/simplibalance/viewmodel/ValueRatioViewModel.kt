package com.nishitnagar.simplibalance.viewmodel

import androidx.lifecycle.ViewModel
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import com.nishitnagar.simplibalance.repositories.ValueRatioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ValueRatioViewModelInterface {
    val valueRatiosFlow: Flow<List<ValueRatioEntity>>

    fun update(valueRatioEntity: ValueRatioEntity)
}

@HiltViewModel
class ValueRatioViewModel @Inject constructor(
    private val valueRatioRepository: ValueRatioRepository
) : ViewModel(), ValueRatioViewModelInterface {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val valueRatiosFlow: Flow<List<ValueRatioEntity>> = valueRatioRepository.getValueRatiosFlow()

    override fun update(valueRatioEntity: ValueRatioEntity) {
        ioScope.launch {
            valueRatioRepository.update(valueRatioEntity)
        }
    }
}