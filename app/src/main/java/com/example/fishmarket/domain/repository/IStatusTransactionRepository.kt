package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import kotlinx.coroutines.flow.Flow

interface IStatusTransactionRepository {

    fun getStatusTransaction(): Flow<List<StatusTransactionEntity>>
}