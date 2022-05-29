package com.example.fishmarket.data.repository.table

import com.example.fishmarket.data.repository.table.source.local.TableLocalDataSource
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.table.source.remote.TableRemoteDataSource
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.ITableRepository
import kotlinx.coroutines.flow.Flow

class TableRepository(
    private val localDataSource: TableLocalDataSource,
    private val remoteDataSource: TableRemoteDataSource
) : ITableRepository {

    override fun addTable(table: TableEntity): Flow<Resource<TableEntity>> {
        return object : NetworkBoundInternetOnly<TableEntity, TableEntity>() {
            override fun loadFromDB(): Flow<TableEntity> = localDataSource.getTable(table.id)

            override suspend fun createCall(): Flow<ApiResponse<TableEntity>> =
                remoteDataSource.addTable(table)

            override suspend fun saveCallResult(data: TableEntity) {
                localDataSource.addTable(table)
            }

        }.asFlow()
    }

    override fun updateTable(table: TableEntity): Flow<Resource<TableEntity>> {
        return object : NetworkBoundInternetOnly<TableEntity, TableEntity>() {
            override fun loadFromDB(): Flow<TableEntity> = localDataSource.getTable(table.id)

            override suspend fun createCall(): Flow<ApiResponse<TableEntity>> =
                remoteDataSource.updateTable(table)

            override suspend fun saveCallResult(data: TableEntity) {
                localDataSource.updateTable(table)
            }

        }.asFlow()
    }

    override fun deleteTable(table: TableEntity): Flow<Resource<List<TableEntity>>> {
        return object : NetworkBoundInternetOnly<List<TableEntity>, TableEntity>() {
            override fun loadFromDB(): Flow<List<TableEntity>> = localDataSource.getTables()

            override suspend fun createCall(): Flow<ApiResponse<TableEntity>> =
                remoteDataSource.deleteTable(table)

            override suspend fun saveCallResult(data: TableEntity) {
                localDataSource.deleteTable(table)
            }

        }.asFlow()
    }

    override fun getTables(): Flow<List<TableEntity>> = localDataSource.getTables()

    override fun getAvailableTable(): Flow<List<TableEntity>> = localDataSource.getAvailableTable()

    override fun getTable(id: String): Flow<TableEntity> = localDataSource.getTable(id)


}