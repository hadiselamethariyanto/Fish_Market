package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    suspend fun addTransaction(transactionEntity: TransactionEntity): Long

    suspend fun changeStatusTransaction(id: Int, status: Int): Int

    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int

    suspend fun setStatusTable(status: Boolean, id: Int)

    fun getTransactions(): Flow<List<TransactionHomeEntity>>
}