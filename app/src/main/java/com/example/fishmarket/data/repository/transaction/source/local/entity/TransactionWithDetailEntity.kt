package com.example.fishmarket.data.repository.transaction.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithDetailEntity(
    @Embedded val transactionEntity: TransactionEntity,
    @Relation(
        parentColumn = "id",
        entity = DetailTransactionHistoryEntity::class,
        entityColumn = "id_transaction"
    )
    val detailTransactionEntity: List<DetailTransactionHistoryEntity>
)