package com.example.fishmarket.data.repository.status_transaction

import com.example.fishmarket.data.repository.status_transaction.source.local.LocalStatusTransactionDataSource
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.data.repository.status_transaction.source.remote.StatusTransactionRemoteDataSource
import com.example.fishmarket.data.repository.status_transaction.source.remote.model.StatusTransactionResponse
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow

class StatusTransactionRepository(
    private val localDaDataSource: LocalStatusTransactionDataSource,
    private val remoteDataSource: StatusTransactionRemoteDataSource
) :
    IStatusTransactionRepository {

    override fun getStatusTransaction(): Flow<Resource<List<StatusTransactionEntity>>> {
        return object :
            NetworkBoundResource<List<StatusTransactionEntity>, List<StatusTransactionResponse>>() {
            override fun loadFromDB(): Flow<List<StatusTransactionEntity>> =
                localDaDataSource.getStatusTransaction()

            override fun shouldFetch(data: List<StatusTransactionEntity>?): Boolean =
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