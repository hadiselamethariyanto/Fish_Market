package com.example.fishmarket.domain.usecase.transaction

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TransactionUseCase {

    fun addTransaction(transaction: TransactionResponse): Flow<Resource<Transaction>>

    fun changeStatusTransaction(transactionEntity: TransactionEntity): Flow<Resource<Transaction>>

    suspend fun setStatusTable(status: Boolean, id: String)

    fun getTransactions(filter: Int): Flow<Resource<List<TransactionHome>>>

    fun getTransactionWithDetail(): Flow<Resource<List<TransactionWithDetail>>>

    fun getRangeTransaction(first: Long, second: Long): Flow<Resource<List<RestaurantTransaction>>>

    fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistory>>

    fun getDetailTransactionRestaurant(idRestaurant: String): Flow<List<DetailTransactionHistory>>
}