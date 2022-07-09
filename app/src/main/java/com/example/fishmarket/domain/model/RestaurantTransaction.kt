package com.example.fishmarket.domain.model

data class RestaurantTransaction(
    val id: String,
    val name: String,
    val income: Double,
    val discount:Int,
    val originalFee:Int,
    val transactionCount: Int
)