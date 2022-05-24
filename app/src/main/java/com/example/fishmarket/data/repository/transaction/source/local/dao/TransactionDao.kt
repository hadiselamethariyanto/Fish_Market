package com.example.fishmarket.data.repository.transaction.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun addTransaction(transaction: TransactionEntity): Long

    @Query("SELECT t.id,t.created_date,t.status,t.id_restaurant,COALESCE(r.name,'') as restaurant, tr.name as `table`,t.id_table,t.dibakar_date,t.disajikan_date,t.finished_date FROM `transaction` t LEFT JOIN restaurant r ON t.id_restaurant = r.id INNER JOIN table_restaurant tr ON t.id_table = tr.id ORDER by created_date DESC")
    fun getTransactions(): Flow<List<TransactionHomeEntity>>

    @Update
    suspend fun changeStatusTransaction(transaction: TransactionEntity): Int

    @Query("UPDATE `transaction` SET id_restaurant =:id_restaurant WHERE id=:id")
    suspend fun updateRestaurantTransaction(id: Int, id_restaurant: Int): Int

    @Query("UPDATE `transaction` SET finished_date =:finished_date WHERE id=:id")
    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int

    @Query("UPDATE table_restaurant SET status =:status WHERE id=:id")
    suspend fun setStatusTable(status: Boolean, id: Int)

}