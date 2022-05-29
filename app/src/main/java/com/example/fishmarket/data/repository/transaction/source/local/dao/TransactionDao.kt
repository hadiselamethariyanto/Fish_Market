package com.example.fishmarket.data.repository.transaction.source.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun addTransaction(transaction: TransactionEntity): Long

    @RawQuery(observedEntities = [TransactionEntity::class, RestaurantEntity::class])
    fun getTransactions(sqLiteQuery: SupportSQLiteQuery): Flow<List<TransactionHomeEntity>>

    @Update
    suspend fun changeStatusTransaction(transaction: TransactionEntity): Int

    @Query("UPDATE `transaction` SET id_restaurant =:id_restaurant WHERE id=:id")
    suspend fun updateRestaurantTransaction(id: Int, id_restaurant: String): Int

    @Query("UPDATE `transaction` SET finished_date =:finished_date WHERE id=:id")
    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int

    @Query("UPDATE table_restaurant SET status =:status WHERE id=:id")
    suspend fun setStatusTable(status: Boolean, id: String)

}