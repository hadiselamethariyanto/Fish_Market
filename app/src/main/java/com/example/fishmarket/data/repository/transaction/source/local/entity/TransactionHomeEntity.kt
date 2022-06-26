package com.example.fishmarket.data.repository.transaction.source.local.entity

data class TransactionHomeEntity(
    val id: String,
    val table: String,
    val id_table: String,
    val id_restaurant: String,
    val restaurant: String,
    val created_date: Long,
    val dibakar_date: Long,
    val disajikan_date: Long,
    val finished_date: Long,
    val status: Int,
    val total_fee: Int,
    val no_urut:Int
)