package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

interface ITableRepository {
    fun addTable(table: TableEntity): Flow<Resource<TableEntity>>

    fun updateTable(table: TableEntity): Flow<Resource<TableEntity>>

    fun deleteTable(table: TableEntity): Flow<Resource<List<TableEntity>>>

    fun getTables(): Flow<Resource<List<TableEntity>>>

    fun getAvailableTable(): Flow<List<TableEntity>>

    fun getTable(id: String): Flow<TableEntity>
}