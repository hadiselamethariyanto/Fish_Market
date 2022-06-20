package com.example.fishmarket.domain.model

data class Transaction(
    val id: String,
    val id_table: String,
    val id_restaurant: String,
    val created_date: Long,
    val dibakar_date: Long,
    val disajikan_date: Long,
    val finished_date: Long,
    val status: Int,
    val total_fee: Int
)