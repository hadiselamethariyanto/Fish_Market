package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionHistoryEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionWithDetailEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    fun addTransaction(transaction: TransactionResponse): Flow<Resource<TransactionEntity>>

    fun changeStatusTransaction(transactionEntity: TransactionEntity): Flow<Resource<TransactionEntity>>

    suspend fun setStatusTable(status: Boolean, id: String)

    fun getTransactions(filter: Int): Flow<Resource<List<TransactionHomeEntity>>>

    fun getTransactionWithDetail(): Flow<Resource<List<TransactionWithDetailEntity>>>

    fun getRangeTransaction(first: Long, second: Long): Flow<Resource<List<TransactionEntity>>>

    fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistoryEntity>>
}