package com.example.fishmarket.data.repository.table

import com.example.fishmarket.data.repository.table.source.local.TableLocalDataSource
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import kotlinx.coroutines.flow.Flow

class TableRepository(private val localDataSource: TableLocalDataSource) : ITableRepository {

    override suspend fun addTable(table: TableEntity) = localDataSource.addTable(table)

    override suspend fun updateTable(table: TableEntity) = localDataSource.updateTable(table)

    override suspend fun deleteTable(table: TableEntity) = localDataSource.deleteTable(table)

    override fun getTables(): Flow<List<TableEntity>> = localDataSource.getTables()

    override fun getTable(id: String): Flow<TableEntity> = localDataSource.getTable(id)


}