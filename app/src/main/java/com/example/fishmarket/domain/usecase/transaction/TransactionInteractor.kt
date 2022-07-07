package com.example.fishmarket.domain.usecase.transaction

import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.*
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionInteractor(private val repository: ITransactionRepository) : TransactionUseCase {

    override fun addTransaction(transaction: TransactionResponse): Flow<Resource<Transaction>> =
        repository.addTransaction(transaction)

    override fun changeStatusTransaction(
        transactionEntity: TransactionEntity,
        detailTransactionEntity: List<DetailTransactionEntity>
    ): Flow<Resource<ChangeStatusTransaction>> =
        repository.changeStatusTransaction(transactionEntity, detailTransactionEntity)

    override fun getTransactions(filter: Int): Flow<Resource<List<TransactionHome>>> =
        repository.getTransactions(filter)

    override fun getTransactionsWithDetail(): Flow<Resource<List<TransactionWithDetail>>> =
        repository.getTransactionsWithDetail()

    override fun getTransactionWithDetail(id: String): Flow<TransactionWithDetail> =
        repository.getTransactionWithDetail(id)

    override fun getRangeTransaction(
        first: Long,
        second: Long
    ): Flow<Resource<List<RestaurantTransaction>>> =
        repository.getRangeTransaction(first, second)

    override fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistory>> =
        repository.getDetailTransaction(id)

    override fun getDetailTransactionRestaurant(
        idRestaurant: String,
        first: Long,
        second: Long
    ): Flow<List<DetailTransactionHistory>> =
        repository.getDetailTransactionRestaurant(idRestaurant, first, second)

    override fun getQueueNumber(): Flow<Int> = repository.getQueueNumber()
}