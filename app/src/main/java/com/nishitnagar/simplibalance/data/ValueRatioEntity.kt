package com.nishitnagar.simplibalance.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Value_Ratio")
data class ValueRatioEntity(
    @PrimaryKey val id: Int = 1,
    var buyIn: Double? = 1.0,
    var chip: Double? = 1.0,
    var money: Double? = 1.0,
)