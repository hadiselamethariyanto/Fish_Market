package com.example.fishmarket.data.repository.table.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTable(table: TableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTables(tables: List<TableEntity>)

    @Update
    suspend fun updateTable(table: TableEntity)

    @Delete
    suspend fun deleteTable(table: TableEntity)

    @Query("SELECT * FROM table_restaurant WHERE id=:id")
    fun getTable(id: String): Flow<TableEntity>

    @Query("SELECT * FROM table_restaurant ORDER BY LENGTH(name),name")
    fun getAvailableTable(): Flow<List<TableEntity>>

    @Query("SELECT * FROM table_restaurant ORDER BY LENGTH(name),name")
    fun getTables(): Flow<List<TableEntity>>
}