package com.example.fishmarket.data.repository.transaction.source.local

import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.*
import com.example.fishmarket.utilis.SortUtils
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSource(private val transactionDao: TransactionDao) {
    suspend fun addTransaction(transaction: TransactionEntity) =
        transactionDao.addTransaction(transaction)

    suspend fun addTransactions(transactions: List<TransactionEntity>) =
        transactionDao.addTransactions(transactions)

    suspend fun addDetailTransactions(detailTransactions: List<DetailTransactionEntity>) =
        transactionDao.addDetailTransactions(detailTransactions)

    suspend fun addRangeTransactions(transactions: List<TransactionEntity>) =
        transactionDao.addRangeTransactions(transactions)

    fun getTransactions(filter: Int): Flow<List<TransactionHomeEntity>> {
        return transactionDao.getTransactions(SortUtils.getSortedQuery(filter))
    }

    fun getTransactionsWithDetail(): Flow<List<TransactionWithDetailEntity>> =
        transactionDao.getTransactionsWithDetail()

    fun getRangeTransactions(first: Long, second: Long) =
        transactionDao.getRangeTransaction(first, second)

    fun getTransaction(id: String): Flow<TransactionEntity> = transactionDao.getTransaction(id)

    suspend fun changeStatusTransaction(transaction: TransactionEntity): Int =
        transactionDao.changeStatusTransaction(transaction)

    suspend fun setStatusTable(status: Boolean, id: String) =
        transactionDao.setStatusTable(status, id)

    fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistoryEntity>> =
        transactionDao.getDetailTransaction(id)
}