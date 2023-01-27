package com.nishitnagar.simplibalance.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Player_Balances")
data class PlayerBalanceEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val name: String,
    var initialValue: Double = 0.0,
    var finalValue: Double = 0.0,
)

class PlayerBalanceEntityProvider : PreviewParameterProvider<PlayerBalanceEntity> {
    override val values = listOf(PlayerBalanceEntity(name = "Nishit")).asSequence()
}