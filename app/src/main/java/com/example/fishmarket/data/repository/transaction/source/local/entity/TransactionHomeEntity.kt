package com.example.fishmarket.data.repository.transaction.source.local.entity

data class TransactionHomeEntity(
    val id: Int,
    val table: String,
    val id_table: Int,
    val id_restaurant: Int,
    val restaurant: String,
    val created_date: Long,
    val dibakar_date: Long,
    val disajikan_date: Long,
    val finished_date: Long,
    val status: Int
)