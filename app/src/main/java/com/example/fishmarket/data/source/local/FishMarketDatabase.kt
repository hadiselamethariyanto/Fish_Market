package com.example.fishmarket.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fishmarket.data.repository.restaurant.source.local.dao.RestaurantDao
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.table.source.local.dao.TableDao
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity

@Database(
    entities = [RestaurantEntity::class, TableEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FishMarketDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun tableDao(): TableDao
    abstract fun transactionDao(): TransactionDao
}