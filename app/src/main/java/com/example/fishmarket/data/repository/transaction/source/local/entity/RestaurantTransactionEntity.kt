package com.example.fishmarket.data.repository.transaction.source.local.entity

data class RestaurantTransactionEntity(
    val id: String,
    val name: String,
    val income: Double,
    val transactionCount: Int
)