package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.StatusTransaction
import kotlinx.coroutines.flow.Flow

interface IStatusTransactionRepository {

    fun getStatusTransaction(): Flow<Resource<List<StatusTransaction>>>
}