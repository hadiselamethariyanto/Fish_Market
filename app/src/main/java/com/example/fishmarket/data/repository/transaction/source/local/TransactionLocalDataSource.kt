package com.example.fishmarket.data.repository.transaction.source.local

import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.utilis.SortUtils
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSource(private val transactionDao: TransactionDao) {
    suspend fun addTransaction(transaction: TransactionEntity) =
        transactionDao.addTransaction(transaction)

    suspend fun addTransactions(transactions: List<TransactionEntity>) =
        transactionDao.addTransactions(transactions)

    fun getTransactions(filter: Int): Flow<List<TransactionHomeEntity>> {
        return transactionDao.getTransactions(SortUtils.getSortedQuery(filter))
    }

    fun getTransaction(id: String): Flow<TransactionEntity> = transactionDao.getTransaction(id)

    suspend fun changeStatusTransaction(transaction: TransactionEntity): Int =
        transactionDao.changeStatusTransaction(transaction)

    suspend fun setStatusTable(status: Boolean, id: String) =
        transactionDao.setStatusTable(status, id)
}