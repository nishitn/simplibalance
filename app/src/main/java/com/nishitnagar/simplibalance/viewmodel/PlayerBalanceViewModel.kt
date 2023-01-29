package com.nishitnagar.simplibalance.viewmodel

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.ViewModel
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.model.SettlementEntity
import com.nishitnagar.simplibalance.repositories.BalanceRepository
import com.nishitnagar.simplibalance.repositories.SettlementRepository
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

    fun deleteAll()

    val settlementEntityFlow: Flow<List<SettlementEntity>>
}

@HiltViewModel
class PlayerBalanceViewModel @Inject constructor(
    private val balanceRepository: BalanceRepository, private val settlementRepository: SettlementRepository
) : ViewModel(), PlayerViewModelInterface {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val playerBalancesFlow: Flow<List<PlayerBalanceEntity>> = balanceRepository.getPlayerBalancesFlow()

    override val settlementEntityFlow: Flow<List<SettlementEntity>> = settlementRepository.getSettlementBalances()

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

    override fun deleteAll() {
        ioScope.launch {
            balanceRepository.deleteAll()
        }
    }
}

class PlayerBalanceViewModelProvider : PreviewParameterProvider<PlayerViewModelInterface> {
    val previewViewModel = object : PlayerViewModelInterface {
        override val playerBalancesFlow: Flow<List<PlayerBalanceEntity>> =
            flowOf(listOf(PlayerBalanceEntity(name = "name1"), PlayerBalanceEntity(name = "name2")))

        override val settlementEntityFlow: Flow<List<SettlementEntity>> =
            flowOf(listOf(SettlementEntity(payerName = "payer1", payeeName = "payee1", amount = 100.0)))

        override fun insert(playerBalanceEntity: PlayerBalanceEntity) {
            /* Not needed */
        }

        override fun update(playerBalanceEntity: PlayerBalanceEntity) {
            /* Not needed */
        }

        override fun delete(playerBalanceEntity: PlayerBalanceEntity) {
            /* Not needed */
        }

        override fun deleteAll() {
            /* Not needed */
        }
    }
    override val values = listOf(previewViewModel).asSequence()
}
