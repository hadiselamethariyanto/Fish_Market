package com.example.fishmarket.domain.usecase.table

import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Table
import kotlinx.coroutines.flow.Flow

interface TableUseCase {
    fun addTable(table: TableEntity): Flow<Resource<Table>>

    fun updateTable(table: TableEntity): Flow<Resource<Table>>

    fun deleteTable(table: TableEntity): Flow<Resource<List<Table>>>

    fun getTables(): Flow<Resource<List<Table>>>

    fun getAvailableTable(): Flow<Resource<List<Table>>>

    fun getTable(id: String): Flow<Table>
}