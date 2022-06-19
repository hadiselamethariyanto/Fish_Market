package com.example.fishmarket.domain.usecase.status_transaction

import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.StatusTransaction
import kotlinx.coroutines.flow.Flow

interface StatusTransactionUseCase {
    fun getStatusTransaction(): Flow<Resource<List<StatusTransaction>>>
}