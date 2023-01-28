package com.nishitnagar.simplibalance.viewmodel

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.ViewModel
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import com.nishitnagar.simplibalance.repositories.ValueRatioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ValueRatioViewModelInterface {
    fun valueRatios(): Flow<List<ValueRatioEntity>>

    fun insert(valueRatioEntity: ValueRatioEntity)
}

@HiltViewModel
class ValueRatioViewModel @Inject constructor(
    private val valueRatioRepository: ValueRatioRepository
) : ViewModel(), ValueRatioViewModelInterface {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun valueRatios(): Flow<List<ValueRatioEntity>> = valueRatioRepository.getValueRatios()

    override fun insert(valueRatioEntity: ValueRatioEntity) {
        ioScope.launch {
            valueRatioRepository.insert(valueRatioEntity)
        }
    }
}

class ValueRatioViewModelProvider : PreviewParameterProvider<ValueRatioViewModelInterface> {
    val previewViewModel = object : ValueRatioViewModelInterface {
        override fun valueRatios(): Flow<List<ValueRatioEntity>> = flowOf(
            listOf(
                ValueRatioEntity(
                    buyIn = 1.0, chip = 8300.0, money = 250.0
                )
            )
        )

        override fun insert(valueRatioEntity: ValueRatioEntity) {
            /* Not needed */
        }
    }
    override val values = listOf(previewViewModel).asSequence()
}
