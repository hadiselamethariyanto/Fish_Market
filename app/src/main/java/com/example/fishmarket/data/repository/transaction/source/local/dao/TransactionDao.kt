package com.example.fishmarket.data.repository.transaction.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun addTransaction(transaction: TransactionEntity): Long

    @Query("SELECT t.id,t.created_date,t.status,r.name as restaurant, tr.name as `table` FROM `transaction` t INNER JOIN restaurant r ON t.id_restaurant = r.id INNER JOIN table_restaurant tr ON t.id_table = tr.id ORDER by created_date DESC")
    fun getTransactions(): Flow<List<TransactionHomeEntity>>

}