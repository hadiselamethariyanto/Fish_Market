package com.example.fishmarket.data.repository.transaction

import com.example.fishmarket.data.repository.transaction.source.local.TransactionLocalDataSource
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionWithDetailEntity
import com.example.fishmarket.data.repository.transaction.source.remote.TransactionRemoteDataSource
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    private val localDataSource: TransactionLocalDataSource,
    private val remoteDataSource: TransactionRemoteDataSource
) :
    ITransactionRepository {

    override fun addTransaction(transaction: TransactionResponse): Flow<Resource<TransactionEntity>> {
        return object : NetworkBoundInternetOnly<TransactionEntity, TransactionResponse>() {
            override fun loadFromDB(): Flow<TransactionEntity> =
                localDataSource.getTransaction(transaction.id.toString())

            override suspend fun createCall(): Flow<ApiResponse<TransactionResponse>> =
                remoteDataSource.addTransaction(transaction)

            override suspend fun saveCallResult(data: TransactionResponse) {
                localDataSource.addTransaction(DataMapper.mapTransactionResponseToEntity(data))
                data.id_table?.let { localDataSource.setStatusTable(true, it) }
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

    override suspend fun setStatusTable(status: Boolean, id: String) =
        localDataSource.setStatusTable(status, id)

    override fun getTransactions(filter: Int): Flow<Resource<List<TransactionHomeEntity>>> {
        return object :
            NetworkBoundResource<List<TransactionHomeEntity>, List<TransactionResponse>>() {
            override fun loadFromDB(): Flow<List<TransactionHomeEntity>> =
                localDataSource.getTransactions(filter)

            override fun shouldFetch(data: List<TransactionHomeEntity>?): Boolean =
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

    override fun getTransactionWithDetail(): Flow<Resource<List<TransactionWithDetailEntity>>> {
        return object :
            NetworkBoundResource<List<TransactionWithDetailEntity>, List<TransactionResponse>>() {
            override fun loadFromDB(): Flow<List<TransactionWithDetailEntity>> =
                localDataSource.getTransactionsWithDetail()

            override fun shouldFetch(data: List<TransactionWithDetailEntity>?): Boolean =
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
    ): Flow<Resource<List<TransactionEntity>>> {
        return object :
            NetworkBoundInternetOnly<List<TransactionEntity>, List<TransactionResponse>>() {
            override fun loadFromDB(): Flow<List<TransactionEntity>> =
                localDataSource.getRangeTransactions(first, second)

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


}