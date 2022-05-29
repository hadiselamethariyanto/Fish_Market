package com.example.fishmarket.data.repository.table.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {
    @Insert
    suspend fun addTable(table: TableEntity)

    @Update
    suspend fun updateTable(table: TableEntity): Int

    @Delete
    suspend fun deleteTable(table: TableEntity)

    @Query("SELECT * FROM table_restaurant WHERE id=:id")
    fun getTable(id: String): Flow<TableEntity>

    @Query("SELECT * FROM table_restaurant WHERE status =0")
    fun getAvailableTable(): Flow<List<TableEntity>>

    @Query("SELECT * FROM table_restaurant ORDER BY id")
    fun getTables(): Flow<List<TableEntity>>
}