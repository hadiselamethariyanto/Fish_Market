package com.example.fishmarket.data.repository.status_transaction.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusTransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatusTransactions(status: List<StatusTransactionEntity>)

    @Query("SELECT * FROM status_transaction ORDER BY id ASC")
    fun getStatusTransaction(): Flow<List<StatusTransactionEntity>>
}