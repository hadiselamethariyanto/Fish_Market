package com.example.fishmarket.data.repository.transaction.source.local

import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSource(private val transactionDao: TransactionDao) {
    suspend fun addTransaction(transaction: TransactionEntity) =
        transactionDao.addTransaction(transaction)

    fun getTransactions(): Flow<List<TransactionHomeEntity>> = transactionDao.getTransactions()

    suspend fun changeStatusTransaction(id: Int, status: Int): Int =
        transactionDao.changeStatusTransaction(id, status)

    suspend fun updateRestaurantTransaction(id: Int, id_restaurant: Int): Int =
        transactionDao.updateRestaurantTransaction(id, id_restaurant)

    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int =
        transactionDao.setFinishedTransaction(id, finished_date)

    suspend fun setStatusTable(status: Boolean, id: Int) = transactionDao.setStatusTable(status, id)
}