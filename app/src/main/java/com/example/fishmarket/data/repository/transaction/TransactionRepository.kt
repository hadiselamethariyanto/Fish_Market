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

    override fun getTransactions(): Flow<List<TransactionHomeEntity>> =
        localDataSource.getTransactions()

}