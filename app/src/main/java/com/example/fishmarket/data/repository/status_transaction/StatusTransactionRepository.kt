package com.example.fishmarket.data.repository.status_transaction

import com.example.fishmarket.data.repository.status_transaction.source.local.LocalStatusTransactionDataSource
import com.example.fishmarket.data.repository.status_transaction.source.remote.StatusTransactionRemoteDataSource
import com.example.fishmarket.data.repository.status_transaction.source.remote.model.StatusTransactionResponse
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.model.StatusTransaction
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StatusTransactionRepository(
    private val localDaDataSource: LocalStatusTransactionDataSource,
    private val remoteDataSource: StatusTransactionRemoteDataSource
) :
    IStatusTransactionRepository {

    override fun getStatusTransaction(): Flow<Resource<List<StatusTransaction>>> {
        return object :
            NetworkBoundResource<List<StatusTransaction>, List<StatusTransactionResponse>>() {
            override fun loadFromDB(): Flow<List<StatusTransaction>> =
                localDaDataSource.getStatusTransaction().map {
                    DataMapper.mapStatusTransactionEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<StatusTransaction>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<StatusTransactionResponse>>> =
                remoteDataSource.getStatusTransaction()

            override suspend fun saveCallResult(data: List<StatusTransactionResponse>) {
                localDaDataSource.insertStatusTransactions(
                    DataMapper.mapStatusTransactionResponseToEntity(
                        data
                    )
                )
            }

        }.asFlow()
    }

}