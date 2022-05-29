package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

interface IStatusTransactionRepository {

    fun getStatusTransaction(): Flow<Resource<List<StatusTransactionEntity>>>
}