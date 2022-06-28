package com.example.fishmarket.data.repository.transaction

import com.example.fishmarket.data.repository.transaction.source.local.TransactionLocalDataSource
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.TransactionRemoteDataSource
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.model.*
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository(
    private val localDataSource: TransactionLocalDataSource,
    private val remoteDataSource: TransactionRemoteDataSource
) :
    ITransactionRepository {

    override fun addTransaction(transaction: TransactionResponse): Flow<Resource<Transaction>> {
        return object : NetworkBoundInternetOnly<Transaction, TransactionResponse>() {
            override fun loadFromDB(): Flow<Transaction> =
                localDataSource.getTransaction(transaction.id.toString()).map {
                    DataMapper.mapTransactionEntityToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<TransactionResponse>> =
                remoteDataSource.addTransaction(transaction)

            override suspend fun saveCallResult(data: TransactionResponse) {
                localDataSource.addTransaction(DataMapper.mapTransactionResponseToEntity(data))
                data.id_table?.let { localDataSource.setStatusTable(true, it) }
            }
        }.asFlow()
    }

    override fun changeStatusTransaction(
        transactionEntity: TransactionEntity,
        detailTransactionEntity: List<DetailTransactionEntity>
    ): Flow<Resource<ChangeStatusTransaction>> {
        return object : NetworkBoundInternetOnly<ChangeStatusTransaction, TransactionEntity>() {
            override fun loadFromDB(): Flow<ChangeStatusTransaction> =
                localDataSource.getChangeStatusTransaction(transactionEntity.id).map {
                    DataMapper.mapChangeStatusTransactionEntityToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<TransactionEntity>> =
                remoteDataSource.updateTransaction(transactionEntity, detailTransactionEntity)

            override suspend fun saveCallResult(data: TransactionEntity) {
                localDataSource.changeStatusTransaction(transactionEntity)
                if (data.status != 4) {
                    localDataSource.setStatusTable(true, data.id_table)
                } else {
                    localDataSource.setStatusTable(false, data.id_table)
                }
                localDataSource.addDetailTransactions(detailTransactionEntity)
            }

        }.asFlow()
    }

    override suspend fun setStatusTable(status: Boolean, id: String) =
        localDataSource.setStatusTable(status, id)

    override fun getTransactions(filter: Int): Flow<Resource<List<TransactionHome>>> {
        return object :
            NetworkBoundResource<List<TransactionHome>, List<TransactionResponse>>() {
            override fun loadFromDB(): Flow<List<TransactionHome>> =
                localDataSource.getTransactions(filter).map {
                    DataMapper.mapTransactionHomeEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<TransactionHome>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<TransactionResponse>>> =
                remoteDataSource.getTransactions()

            override suspend fun saveCallResult(data: List<TransactionResponse>) {
                localDataSource.addTransactions(DataMapper.mapTransactionResponseToEntity(data))
                for (x in data) {
                    val detail = x.detail
                    if (detail != null) {
                        localDataSource.addDetailTransactions(
                            DataMapper.mapDetailTransactionsToEntity(
                                detail, x.id.toString()
                            )
                        )
                    }
                }
            }

        }.asFlow()
    }

    override fun getTransactionWithDetail(): Flow<Resource<List<TransactionWithDetail>>> {
        return object :
            NetworkBoundResource<List<TransactionWithDetail>, List<TransactionResponse>>() {
            override fun loadFromDB(): Flow<List<TransactionWithDetail>> =
                localDataSource.getTransactionsWithDetail().map {
                    DataMapper.mapTransactionWithDetailEntityToDomain(it)
                }

            override fun shouldFetch(data: List<TransactionWithDetail>?): Boolean =
                data == null || data.size < 50

            override suspend fun createCall(): Flow<ApiResponse<List<TransactionResponse>>> =
                remoteDataSource.getLastFiftyTransactions()

            override suspend fun saveCallResult(data: List<TransactionResponse>) {
                localDataSource.addTransactions(DataMapper.mapTransactionResponseToEntity(data))
                for (x in data) {
                    val detail = x.detail
                    if (detail != null) {
                        localDataSource.addDetailTransactions(
                            DataMapper.mapDetailTransactionsToEntity(
                                detail, x.id.toString()
                            )
                        )
                    }
                }
            }

        }.asFlow()
    }

    override fun getRangeTransaction(
        first: Long,
        second: Long
    ): Flow<Resource<List<RestaurantTransaction>>> {
        return object :
            NetworkBoundInternetOnly<List<RestaurantTransaction>, List<TransactionResponse>>() {
            override fun loadFromDB(): Flow<List<RestaurantTransaction>> =
                localDataSource.getRangeTransactions(first, second).map {
                    DataMapper.mapRestaurantTransactionToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<List<TransactionResponse>>> =
                remoteDataSource.getRangeTransaction(first, second)

            override suspend fun saveCallResult(data: List<TransactionResponse>) {
                localDataSource.addRangeTransactions(DataMapper.mapTransactionResponseToEntity(data))
                for (x in data) {
                    val detail = x.detail
                    if (detail != null) {
                        localDataSource.addDetailTransactions(
                            DataMapper.mapDetailTransactionsToEntity(
                                detail, x.id.toString()
                            )
                        )
                    }
                }
            }

        }.asFlow()
    }

    override fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistory>> =
        localDataSource.getDetailTransaction(id).map {
            DataMapper.mapDetailTransactionHistoryEntitiesToDomain(it)
        }

    override fun getDetailTransactionRestaurant(
        idRestaurant: String,
        first: Long,
        second: Long
    ): Flow<List<DetailTransactionHistory>> =
        localDataSource.getDetailTransactionRestaurant(idRestaurant, first, second).map {
            DataMapper.mapDetailTransactionHistoryEntitiesToDomain(it)
        }

    override fun getQueueNumber(): Flow<Int> = localDataSource.getQueueNumber()


}