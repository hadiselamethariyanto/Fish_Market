package com.example.fishmarket.domain.model

data class TransactionFire(
    val id: Int,
    val name: String,
    val id_restaurant: String,
    val created_date: Long,
    val status: Int
)