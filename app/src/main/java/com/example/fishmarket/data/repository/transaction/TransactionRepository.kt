package com.example.fishmarket.data.repository.transaction

import com.example.fishmarket.data.repository.transaction.source.local.TransactionLocalDataSource
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val localDataSource: TransactionLocalDataSource) :
    ITransactionRepository {

    override suspend fun addTransaction(transactionEntity: TransactionEntity) =
        localDataSource.addTransaction(transactionEntity)

    override suspend fun changeStatusTransaction(transactionEntity: TransactionEntity): Int =
        localDataSource.changeStatusTransaction(transactionEntity)

    override suspend fun updateRestaurantTransaction(id: Int, id_restaurant: String): Int =
        localDataSource.updateRestaurantTransaction(id, id_restaurant)

    override suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int =
        localDataSource.setFinishedTransaction(id, finished_date)

    override suspend fun setStatusTable(status: Boolean, id: Int) =
        localDataSource.setStatusTable(status, id)

    override fun getTransactions(filter: Int): Flow<List<TransactionHomeEntity>> =
        localDataSource.getTransactions(filter)

}