package com.example.fishmarket.data.repository.transaction

import com.example.fishmarket.data.repository.transaction.source.local.TransactionLocalDataSource
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.repository.transaction.source.remote.TransactionRemoteDataSource
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    private val localDataSource: TransactionLocalDataSource,
    private val remoteDataSource: TransactionRemoteDataSource
) :
    ITransactionRepository {

    override fun addTransaction(transactionEntity: TransactionEntity): Flow<Resource<TransactionEntity>> {
        return object : NetworkBoundInternetOnly<TransactionEntity, TransactionEntity>() {
            override fun loadFromDB(): Flow<TransactionEntity> =
                localDataSource.getTransaction(transactionEntity.id)

            override suspend fun createCall(): Flow<ApiResponse<TransactionEntity>> =
                remoteDataSource.addTransaction(transactionEntity)

            override suspend fun saveCallResult(data: TransactionEntity) {
                localDataSource.addTransaction(data)
                localDataSource.setStatusTable(true, data.id_table)
            }
        }.asFlow()
    }

    override fun changeStatusTransaction(transactionEntity: TransactionEntity): Flow<Resource<TransactionEntity>> {
        return object : NetworkBoundInternetOnly<TransactionEntity, TransactionEntity>() {
            override fun loadFromDB(): Flow<TransactionEntity> =
                localDataSource.getTransaction(transactionEntity.id)

            override suspend fun createCall(): Flow<ApiResponse<TransactionEntity>> =
                remoteDataSource.updateTransaction(transactionEntity)

            override suspend fun saveCallResult(data: TransactionEntity) {
                localDataSource.changeStatusTransaction(transactionEntity)
                if (data.status != 4) {
                    localDataSource.setStatusTable(true, data.id_table)
                } else {
                    localDataSource.setStatusTable(false, data.id_table)
                }
            }

        }.asFlow()
    }

    override suspend fun updateRestaurantTransaction(id: Int, id_restaurant: String): Int =
        localDataSource.updateRestaurantTransaction(id, id_restaurant)

    override suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int =
        localDataSource.setFinishedTransaction(id, finished_date)

    override suspend fun setStatusTable(status: Boolean, id: String) =
        localDataSource.setStatusTable(status, id)

    override fun getTransactions(filter: Int): Flow<List<TransactionHomeEntity>> =
        localDataSource.getTransactions(filter)

}