package com.example.fishmarket.domain.model

data class TransactionWithDetail(
    val transaction: Transaction,
    val detailTransactionHistory: List<DetailTransactionHistory>
)