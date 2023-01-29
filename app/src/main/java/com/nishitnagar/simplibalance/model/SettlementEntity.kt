package com.nishitnagar.simplibalance.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class SettlementEntity(
    val payerName: String,
    val payeeName: String,
    val amount: Double
)


class SettlementEntityProvider : PreviewParameterProvider<SettlementEntity> {
    override val values = listOf(
        SettlementEntity(payerName = "Nishit", payeeName = "Nithin", amount = 100.0),
        SettlementEntity(payerName = "Someone", payeeName = "What", amount = 250.5),
    ).asSequence()
}