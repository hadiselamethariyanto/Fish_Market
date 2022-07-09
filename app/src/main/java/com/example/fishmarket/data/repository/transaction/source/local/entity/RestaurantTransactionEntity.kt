package com.example.fishmarket.data.repository.transaction.source.local.entity

data class RestaurantTransactionEntity(
    val id: String,
    val name: String,
    val income: Double,
    val discount:Int,
    val original_fee:Int,
    val transactionCount: Int
)