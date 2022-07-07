package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    fun addTransaction(transaction: TransactionResponse): Flow<Resource<Transaction>>

    fun changeStatusTransaction(
        transactionEntity: TransactionEntity,
        detailTransactionEntity: List<DetailTransactionEntity>
    ): Flow<Resource<ChangeStatusTransaction>>

    fun getTransactions(filter: Int): Flow<Resource<List<TransactionHome>>>

    fun getTransactionsWithDetail(): Flow<Resource<List<TransactionWithDetail>>>

    fun getTransactionWithDetail(id: String): Flow<TransactionWithDetail>

    fun getRangeTransaction(first: Long, second: Long): Flow<Resource<List<RestaurantTransaction>>>

    fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistory>>

    fun getDetailTransactionRestaurant(
        idRestaurant: String,
        first: Long,
        second: Long
    ): Flow<List<DetailTransactionHistory>>

    fun getQueueNumber(): Flow<Int>
}