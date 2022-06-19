package com.example.fishmarket.domain.model

data class RestaurantWithTransaction(
    val restaurant: Restaurant,
    val transaction: List<TransactionFire>
)