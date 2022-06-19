package com.example.fishmarket.domain.usecase.table

import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.domain.repository.ITableRepository
import kotlinx.coroutines.flow.Flow

class TableInteractor(private val repository: ITableRepository) : TableUseCase {
    override fun addTable(table: TableEntity): Flow<Resource<Table>> = repository.addTable(table)

    override fun updateTable(table: TableEntity): Flow<Resource<Table>> =
        repository.updateTable(table)

    override fun deleteTable(table: TableEntity): Flow<Resource<List<Table>>> =
        repository.deleteTable(table)

    override fun getTables(): Flow<Resource<List<Table>>> = repository.getTables()

    override fun getAvailableTable(): Flow<Resource<List<Table>>> = repository.getAvailableTable()

    override fun getTable(id: String): Flow<Table> = repository.getTable(id)
}