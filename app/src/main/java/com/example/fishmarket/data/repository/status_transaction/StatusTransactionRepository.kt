package com.example.fishmarket.data.repository.status_transaction

import com.example.fishmarket.data.repository.status_transaction.source.local.LocalStatusTransactionDataSource
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import kotlinx.coroutines.flow.Flow

class StatusTransactionRepository(private val localDaDataSource: LocalStatusTransactionDataSource) :
    IStatusTransactionRepository {

    override fun getStatusTransaction(): Flow<List<StatusTransactionEntity>> =
        localDaDataSource.getStatusTransaction()
}