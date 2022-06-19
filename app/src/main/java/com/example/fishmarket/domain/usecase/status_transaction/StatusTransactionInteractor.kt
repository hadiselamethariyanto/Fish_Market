package com.example.fishmarket.domain.usecase.status_transaction

import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.StatusTransaction
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import kotlinx.coroutines.flow.Flow

class StatusTransactionInteractor(private val repository: IStatusTransactionRepository) :
    StatusTransactionUseCase {

    override fun getStatusTransaction(): Flow<Resource<List<StatusTransaction>>> =
        repository.getStatusTransaction()
}