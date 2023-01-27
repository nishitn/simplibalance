package com.nishitnagar.simplibalance.viewmodel

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.ViewModel
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.repositories.BalanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PlayerViewModelInterface {
    val playerBalancesFlow: Flow<List<PlayerBalanceEntity>>

    fun insert(playerBalanceEntity: PlayerBalanceEntity)

    fun update(playerBalanceEntity: PlayerBalanceEntity)

    fun delete(playerBalanceEntity: PlayerBalanceEntity)
}

@HiltViewModel
class PlayerBalanceViewModel @Inject constructor(
    private val balanceRepository: BalanceRepository
) : ViewModel(), PlayerViewModelInterface {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val playerBalancesFlow: Flow<List<PlayerBalanceEntity>> = balanceRepository.getPlayerBalancesFlow()

    override fun insert(playerBalanceEntity: PlayerBalanceEntity) {
        ioScope.launch {
            balanceRepository.insert(playerBalanceEntity)
        }
    }

    override fun update(playerBalanceEntity: PlayerBalanceEntity) {
        ioScope.launch {
            balanceRepository.update(playerBalanceEntity)
        }
    }

    override fun delete(playerBalanceEntity: PlayerBalanceEntity) {
        ioScope.launch {
            balanceRepository.delete(playerBalanceEntity)
        }
    }
}

class PlayerBalanceViewModelProvider : PreviewParameterProvider<PlayerViewModelInterface> {
    val previewViewModel = object : PlayerViewModelInterface {
        override val playerBalancesFlow: Flow<List<PlayerBalanceEntity>>
            get() = flowOf(listOf(PlayerBalanceEntity(name = "name1")))

        override fun insert(playerBalanceEntity: PlayerBalanceEntity) {
            TODO("Not yet implemented")
        }

        override fun update(playerBalanceEntity: PlayerBalanceEntity) {
            TODO("Not yet implemented")
        }

        override fun delete(playerBalanceEntity: PlayerBalanceEntity) {
            TODO("Not yet implemented")
        }
    }
    override val values = listOf(previewViewModel).asSequence()
}
