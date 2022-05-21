package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import kotlinx.coroutines.flow.Flow

interface ITableRepository {
    suspend fun addTable(table: TableEntity): Long

    suspend fun updateTable(table: TableEntity): Int

    suspend fun deleteTable(table: TableEntity)

    fun getTables(): Flow<List<TableEntity>>

    fun getTable(id: String): Flow<TableEntity>
}