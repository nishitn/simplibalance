package com.nishitnagar.simplibalance.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Player_Balances")
data class PlayerBalanceEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val name: String,
    var buyIns: Double? = 0.0,
    var initialChips: Double? = 0.0,
    var finalChips: Double? = 0.0,
)