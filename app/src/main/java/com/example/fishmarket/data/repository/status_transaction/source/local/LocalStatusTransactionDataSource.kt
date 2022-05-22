package com.example.fishmarket.data.repository.status_transaction.source.local

import com.example.fishmarket.data.repository.status_transaction.source.local.dao.StatusTransactionDao
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import kotlinx.coroutines.flow.Flow

class LocalStatusTransactionDataSource(private val statusDao: StatusTransactionDao) {
    fun getStatusTransaction(): Flow<List<StatusTransactionEntity>> =
        statusDao.getStatusTransaction()
}