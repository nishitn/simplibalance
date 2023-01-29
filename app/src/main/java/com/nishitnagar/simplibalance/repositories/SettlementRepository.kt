package com.nishitnagar.simplibalance.repositories

import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import com.nishitnagar.simplibalance.model.SettlementEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Math.abs

class SettlementRepository(
    private val balanceRepository: BalanceRepository,
    private val valueRatioRepository: ValueRatioRepository,
) {
    fun getSettlementBalances(): Flow<List<SettlementEntity>> {
        return flow {
            val playerBalances = balanceRepository.getPlayerBalancesList()
            val valueRatioEntity = valueRatioRepository.getValueRatiosList()[0]

            val simplifiedPlayerBalances =
                getSimplifiedPlayerBalances(playerBalances = playerBalances, valueRatioEntity = valueRatioEntity)

            val settlementBalances = getSettlementBalances(simplifiedPlayerBalances)

            emit(settlementBalances)
        }
    }

    private fun getSimplifiedPlayerBalances(
        playerBalances: List<PlayerBalanceEntity>, valueRatioEntity: ValueRatioEntity
    ): List<SimplifiedPlayerBalance> {
        val buyInRatio = valueRatioEntity.money!! / valueRatioEntity.buyIn!!
        val chipRatio = valueRatioEntity.money!! / valueRatioEntity.chip!!

        val simplifiedPlayerBalances = playerBalances.map {
            val balance = it.buyIns!! * buyInRatio + (it.initialValue!! - it.finalValue!!) * chipRatio
            SimplifiedPlayerBalance(it.name, balance)
        }.toList()

        return simplifiedPlayerBalances
    }

    private fun getSettlementBalances(simplifiedPlayerBalances: List<SimplifiedPlayerBalance>): List<SettlementEntity> {
        val sortedPlayerBalances = simplifiedPlayerBalances.sortedByDescending { it.balance }.toList()
        val settlementEntities = mutableListOf<SettlementEntity>()

        var l = 0
        var r = sortedPlayerBalances.size - 1
        while (l < r) {
            val payer = sortedPlayerBalances[r]
            val payee = sortedPlayerBalances[l]

            val diff = payee.balance + payer.balance
            if (diff > 0) {
                settlementEntities.add(SettlementEntity(payer.name, payee.name, abs(payer.balance)))
                payee.balance = diff
                r -= 1
            } else if (diff < 0) {
                settlementEntities.add(SettlementEntity(payer.name, payee.name, abs(payee.balance)))
                payer.balance = diff
                l += 1
            } else {
                settlementEntities.add(SettlementEntity(payer.name, payee.name, abs(payee.balance)))
                r -= 1
                l += 1
            }
        }

        return settlementEntities
    }
}

private data class SimplifiedPlayerBalance(
    val name: String, var balance: Double
)
