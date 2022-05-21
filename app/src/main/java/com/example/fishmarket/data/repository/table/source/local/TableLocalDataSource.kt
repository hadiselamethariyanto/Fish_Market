package com.example.fishmarket.data.repository.table.source.local

import com.example.fishmarket.data.repository.table.source.local.dao.TableDao
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity

class TableLocalDataSource(private val tableDao: TableDao) {
    suspend fun addTable(table: TableEntity) = tableDao.addTable(table)

    suspend fun updateTable(table: TableEntity) = tableDao.updateTable(table)

    suspend fun deleteTable(table: TableEntity) = tableDao.deleteTable(table)

    fun getTable(id: String) = tableDao.getTable(id)

    fun getTables() = tableDao.getTables()
}