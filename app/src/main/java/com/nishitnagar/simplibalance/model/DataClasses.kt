package com.nishitnagar.simplibalance.model


data class SimplifiedPlayerBalance(
    val name: String, var balance: Double
)

data class CustomError(
    val type: ErrorTypes, val description: String
)