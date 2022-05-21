package com.example.fishmarket.data.repository.transaction.source.local.entity

data class TransactionHomeEntity(
    val id: Int,
    val table: String,
    val restaurant: String,
    val created_date: Long,
    val status: Int
)