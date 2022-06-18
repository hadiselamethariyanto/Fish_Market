package com.example.fishmarket.data.repository.table

import com.example.fishmarket.data.repository.table.source.local.TableLocalDataSource
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.table.source.remote.TableRemoteDataSource
import com.example.fishmarket.data.repository.table.source.remote.model.TableResponse
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.domain.repository.ITableRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TableRepository(
    private val localDataSource: TableLocalDataSource,
    private val remoteDataSource: TableRemoteDataSource
) : ITableRepository {

    override fun addTable(table: TableEntity): Flow<Resource<Table>> {
        return object : NetworkBoundInternetOnly<Table, TableEntity>() {
            override fun loadFromDB(): Flow<Table> = localDataSource.getTable(table.id).map {
                DataMapper.mapTableEntityToDomain(it)
            }

            override suspend fun createCall(): Flow<ApiResponse<TableEntity>> =
                remoteDataSource.addTable(table)

            override suspend fun saveCallResult(data: TableEntity) {
                localDataSource.addTable(table)
            }

        }.asFlow()
    }

    override fun updateTable(table: TableEntity): Flow<Resource<Table>> {
        return object : NetworkBoundInternetOnly<Table, TableEntity>() {
            override fun loadFromDB(): Flow<Table> = localDataSource.getTable(table.id).map {
                DataMapper.mapTableEntityToDomain(it)
            }

            override suspend fun createCall(): Flow<ApiResponse<TableEntity>> =
                remoteDataSource.updateTable(table)

            override suspend fun saveCallResult(data: TableEntity) {
                localDataSource.updateTable(table)
            }

        }.asFlow()
    }

    override fun deleteTable(table: TableEntity): Flow<Resource<List<Table>>> {
        return object : NetworkBoundInternetOnly<List<Table>, TableEntity>() {
            override fun loadFromDB(): Flow<List<Table>> = localDataSource.getTables().map {
                DataMapper.mapTableEntitiesToDomain(it)
            }

            override suspend fun createCall(): Flow<ApiResponse<TableEntity>> =
                remoteDataSource.deleteTable(table)

            override suspend fun saveCallResult(data: TableEntity) {
                localDataSource.deleteTable(table)
            }

        }.asFlow()
    }

    override fun getTables(): Flow<Resource<List<Table>>> {
        return object : NetworkBoundResource<List<Table>, List<TableResponse>>() {
            override fun loadFromDB(): Flow<List<Table>> = localDataSource.getTables().map {
                DataMapper.mapTableEntitiesToDomain(it)
            }

            override fun shouldFetch(data: List<Table>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TableResponse>>> =
                remoteDataSource.getTables()

            override suspend fun saveCallResult(data: List<TableResponse>) {
                localDataSource.addTables(DataMapper.mapTableResponseToEntity(data))
            }

        }.asFlow()
    }

    override fun getAvailableTable(): Flow<Resource<List<Table>>> {
        return object : NetworkBoundResource<List<Table>, List<TableResponse>>() {
            override fun loadFromDB(): Flow<List<Table>> = localDataSource.getAvailableTable().map {
                DataMapper.mapTableEntitiesToDomain(it)
            }

            override fun shouldFetch(data: List<Table>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TableResponse>>> =
                remoteDataSource.getTables()

            override suspend fun saveCallResult(data: List<TableResponse>) {
                localDataSource.addTables(DataMapper.mapTableResponseToEntity(data))
            }

        }.asFlow()
    }

    override fun getTable(id: String): Flow<Table> = localDataSource.getTable(id).map {
        DataMapper.mapTableEntityToDomain(it)
    }


}