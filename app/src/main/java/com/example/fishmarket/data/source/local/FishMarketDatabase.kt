package com.example.fishmarket.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fishmarket.data.repository.category.source.local.dao.CategoryDao
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.repository.login.source.local.dao.UserDao
import com.example.fishmarket.data.repository.login.source.local.entity.UserEntity
import com.example.fishmarket.data.repository.menu.source.local.dao.MenuDao
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.repository.restaurant.source.local.dao.RestaurantDao
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.status_transaction.source.local.dao.StatusTransactionDao
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.data.repository.table.source.local.dao.TableDao
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.transaction.source.local.dao.TransactionDao
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionHistoryEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionFireEntity

@Database(
    entities = [RestaurantEntity::class, TableEntity::class, TransactionEntity::class,
        StatusTransactionEntity::class, MenuEntity::class, CategoryEntity::class,
        DetailTransactionEntity::class, UserEntity::class],
    views = [TransactionFireEntity::class, DetailTransactionHistoryEntity::class],
    version = 4,
    exportSchema = false
)
abstract class FishMarketDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun tableDao(): TableDao
    abstract fun transactionDao(): TransactionDao
    abstract fun statusTransactionDao(): StatusTransactionDao
    abstract fun menuDao(): MenuDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
}