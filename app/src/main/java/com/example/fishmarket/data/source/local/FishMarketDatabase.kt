package com.example.fishmarket.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fishmarket.data.repository.restaurant.source.local.dao.RestaurantDao
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity

@Database(
    entities = [RestaurantEntity::class],
    version = 1
)
abstract class FishMarketDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}