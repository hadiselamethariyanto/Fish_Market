package com.example.fishmarket.data.repository.transaction.source.local

import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSource(private val transactionDao: TransactionDao) {
    suspend fun addTransaction(transaction: TransactionEntity) =
        transactionDao.addTransaction(transaction)

    fun getTransactions(): Flow<List<TransactionHomeEntity>> = transactionDao.getTransactions()
}