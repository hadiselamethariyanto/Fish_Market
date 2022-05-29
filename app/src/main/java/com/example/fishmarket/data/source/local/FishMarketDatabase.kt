package com.example.fishmarket.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fishmarket.data.repository.restaurant.source.local.dao.RestaurantDao
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.status_transaction.source.local.dao.StatusTransactionDao
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.data.repository.table.source.local.dao.TableDao
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionFireEntity

@Database(
    entities = [RestaurantEntity::class, TableEntity::class, TransactionEntity::class, StatusTransactionEntity::class],
    views = [TransactionFireEntity::class],
    version = 3,
    exportSchema = false
)
abstract class FishMarketDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun tableDao(): TableDao
    abstract fun transactionDao(): TransactionDao
    abstract fun statusTransactionDao(): StatusTransactionDao
}