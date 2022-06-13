package com.example.fishmarket.domain.model

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionWithDetailEntity

data class History(
    val viewType: Int,
    val data: TransactionWithDetailEntity
)