package com.example.fishmarket.data.repository.transaction.source.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.*
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransactions(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDetailTransactions(detailTransactions: List<DetailTransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRangeTransactions(transactions: List<TransactionEntity>)

    @RawQuery(observedEntities = [TransactionEntity::class, RestaurantEntity::class])
    fun getTransactions(sqLiteQuery: SupportSQLiteQuery): Flow<List<TransactionHomeEntity>>

    @Query("SELECT * FROM `transaction` WHERE id=:id")
    fun getTransaction(id: String): Flow<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM `transaction` ORDER BY created_date DESC LIMIT 50")
    fun getTransactionsWithDetail(): Flow<List<TransactionWithDetailEntity>>

    @Query("SELECT * FROM `transaction` WHERE date(created_date/1000,'unixepoch','localtime')>= date(:first/1000,'unixepoch','localtime') AND date(created_date/1000,'unixepoch','localtime') <= date(:second/1000,'unixepoch','localtime')")
    fun getRangeTransaction(first: Long, second: Long): Flow<List<TransactionEntity>>

    @Update
    suspend fun changeStatusTransaction(transaction: TransactionEntity): Int

    @Query("UPDATE `transaction` SET id_restaurant =:id_restaurant WHERE id=:id")
    suspend fun updateRestaurantTransaction(id: Int, id_restaurant: String): Int

    @Query("UPDATE `transaction` SET finished_date =:finished_date WHERE id=:id")
    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int

    @Query("UPDATE table_restaurant SET status =:status WHERE id=:id")
    suspend fun setStatusTable(status: Boolean, id: String)

    @Query("SELECT dt.id, dt.id_transaction,m.name,dt.quantity,dt.price,m.unit from detail_transaction dt INNER JOIN menu m ON dt.id_menu = m.id WHERE dt.id_transaction = :id")
    fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistoryEntity>>

}