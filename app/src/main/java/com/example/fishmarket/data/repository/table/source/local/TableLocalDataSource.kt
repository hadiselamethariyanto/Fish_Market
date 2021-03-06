package com.example.fishmarket.data.repository.table.source.local

import com.example.fishmarket.data.repository.table.source.local.dao.TableDao
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity

class TableLocalDataSource(private val tableDao: TableDao) {
    suspend fun addTable(table: TableEntity) = tableDao.addTable(table)

    suspend fun addTables(tables: List<TableEntity>) = tableDao.addTables(tables)

    suspend fun updateTable(table: TableEntity) = tableDao.updateTable(table)

    suspend fun deleteTable(table: TableEntity) = tableDao.deleteTable(table)

    fun getTable(id: String) = tableDao.getTable(id)

    fun getAvailableTable() = tableDao.getAvailableTable()

    fun getTables() = tableDao.getTables()
}