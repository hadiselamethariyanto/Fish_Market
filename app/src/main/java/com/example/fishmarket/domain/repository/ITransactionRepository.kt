package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    suspend fun addTransaction(transactionEntity: TransactionEntity): Long

    suspend fun changeStatusTransaction(transactionEntity: TransactionEntity): Int

    suspend fun updateRestaurantTransaction(id: Int, id_restaurant: String): Int

    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int

    suspend fun setStatusTable(status: Boolean, id: Int)

    fun getTransactions(filter:Int): Flow<List<TransactionHomeEntity>>
}