package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    suspend fun addTransaction(transactionEntity: TransactionEntity): Long

    fun getTransactions(): Flow<List<TransactionHomeEntity>>
}