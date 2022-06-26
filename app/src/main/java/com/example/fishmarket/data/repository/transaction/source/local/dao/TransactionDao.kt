package com.example.fishmarket.data.repository.transaction.source.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransactions(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDetailTransactions(detailTransactions: List<DetailTransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRangeTransactions(transactions: List<TransactionEntity>)

    @RawQuery(observedEntities = [TransactionEntity::class, RestaurantEntity::class])
    fun getTransactions(sqLiteQuery: SupportSQLiteQuery): Flow<List<TransactionHomeEntity>>

    @Query("SELECT * FROM `transaction` WHERE id=:id")
    fun getTransaction(id: String): Flow<TransactionEntity>

    @Query(
        "SELECT t.*,tr.name as table_name,r.name as restaurant_name FROM `transaction` t " +
                "INNER JOIN table_restaurant tr ON t.id_table = tr.id " +
                "INNER JOIN restaurant r ON t.id_restaurant = r.id " +
                "WHERE t.id=:id"
    )
    fun getChangeStatusTransaction(id: String): Flow<ChangeStatusTransactionEntity>

    @Transaction
    @Query("SELECT * FROM `transaction` ORDER BY created_date DESC LIMIT 50")
    fun getTransactionsWithDetail(): Flow<List<TransactionWithDetailEntity>>

    @Query(
        "SELECT r.id,r.name, SUM(t.total_fee) as income, COUNT(t.id) as transactionCount FROM restaurant r " +
                "INNER JOIN `transaction` t ON r.id = t.id_restaurant " +
                "WHERE date(t.created_date/1000,'unixepoch','localtime')>= date(:first/1000,'unixepoch','localtime') " +
                "AND date(t.created_date/1000,'unixepoch','localtime') <= date(:second/1000,'unixepoch','localtime') GROUP BY r.name"
    )
    fun getRangeTransaction(first: Long, second: Long): Flow<List<RestaurantTransactionEntity>>

    @Update
    suspend fun changeStatusTransaction(transaction: TransactionEntity): Int

    @Query("UPDATE `transaction` SET id_restaurant =:id_restaurant WHERE id=:id")
    suspend fun updateRestaurantTransaction(id: Int, id_restaurant: String): Int

    @Query("UPDATE `transaction` SET finished_date =:finished_date WHERE id=:id")
    suspend fun setFinishedTransaction(id: Int, finished_date: Long): Int

    @Query("UPDATE table_restaurant SET status =:status WHERE id=:id")
    suspend fun setStatusTable(status: Boolean, id: String)

    @Query("SELECT dt.id, dt.id_transaction,m.name,dt.quantity,dt.price,m.unit,dt.status,m.id as id_menu from detail_transaction dt INNER JOIN menu m ON dt.id_menu = m.id WHERE dt.id_transaction = :id")
    fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistoryEntity>>

    @Query(
        "SELECT dt.id, dt.id_transaction,m.name,dt.quantity,dt.price,m.unit,dt.status,m.id as id_menu from `transaction` t " +
                "INNER JOIN  detail_transaction dt ON t.id = dt.id_transaction " +
                "INNER JOIN menu m ON dt.id_menu = m.id WHERE t.id_restaurant = :idRestaurant"
    )
    fun getDetailTransactionRestaurant(idRestaurant: String): Flow<List<DetailTransactionHistoryEntity>>


    @Query("SELECT MAX(no_urut)  FROM `transaction` WHERE DATE(created_date/1000,'unixepoch','localtime') = DATE('now','localtime')")
    fun getQueueNumber(): Flow<Int>

}