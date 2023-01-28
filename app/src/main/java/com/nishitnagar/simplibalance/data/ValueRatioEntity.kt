package com.nishitnagar.simplibalance.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Value_Ratio")
data class ValueRatioEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var buyIn: Double = 0.0,
    var chip: Double = 0.0,
    var money: Double = 0.0,
)