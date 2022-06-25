package com.example.fishmarket.domain.model

data class DetailTransactionHistory(
    val id: String,
    val id_transaction: String,
    val id_menu: String,
    val name: String,
    val quantity: Double,
    val price: Int,
    val unit: String,
    var status: Boolean
)