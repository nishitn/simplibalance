package com.nishitnagar.simplibalance.repositories

import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import com.nishitnagar.simplibalance.model.CustomError
import com.nishitnagar.simplibalance.model.ErrorTypes
import com.nishitnagar.simplibalance.model.SettlementEntity
import com.nishitnagar.simplibalance.model.SimplifiedPlayerBalance
import com.nishitnagar.simplibalance.utils.decimalFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.math.abs

class SettlementRepository(
    private val balanceRepository: BalanceRepository,
    private val valueRatioRepository: ValueRatioRepository,
) {
    fun getPossibleSettlementErrors(): List<CustomError> {
        val errors = mutableListOf<CustomError>()
        runBlocking {
            val playerBalances = balanceRepository.getPlayerBalancesList()

            val valueRatioEntity = valueRatioRepository.getValueRatiosList()[0]

            if (valueRatioEntity.buyIn == null || valueRatioEntity.chip == null || valueRatioEntity.money == null) {
                errors.add(CustomError(ErrorTypes.BLOCKING, "Incorrect input found for BuyIn|Chip|Money"))
            }

            playerBalances.filter { it.initialChips == null || it.finalChips == null }.forEach {
                errors.add(CustomError(ErrorTypes.BLOCKING, "Incorrect input found for ${it.name}"))
            }

            if (errors.isEmpty()) {
                val simplifiedPlayerBalances = getSimplifiedPlayerBalances(playerBalances, valueRatioEntity)

                val amountMismatch = simplifiedPlayerBalances.sumOf { it.balance }

                val message = if (amountMismatch > 0) {
                    "Money mismatch. Final amount is extra by ${decimalFormat.format(abs(amountMismatch))}"
                } else {
                    "Money mismatch. Final amount is missing ${decimalFormat.format(abs(amountMismatch))}"
                }

                if (abs(amountMismatch) > 1E-4) {
                    errors.add(CustomError(ErrorTypes.DISMISSIBLE, message))
                }
            }
        }

        return errors
    }

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
            val balance = (it.finalChips!! - it.initialChips!!) * chipRatio - it.buyIns!! * buyInRatio
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