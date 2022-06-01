package com.example.fishmarket.data.repository.restaurant.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<RestaurantEntity>)

    @Delete
    suspend fun deleteRestaurant(restaurant: RestaurantEntity)

    @Update
    suspend fun updateRestaurant(restaurant: RestaurantEntity): Int

    @Query("SELECT * FROM restaurant ORDER BY name ASC")
    fun getRestaurant(): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM restaurant WHERE id=:id")
    fun getRestaurant(id: String): Flow<RestaurantEntity>

    @Transaction
    @Query("SELECT * FROM `restaurant` ORDER BY name ASC")
    fun getRestaurantWithTransaction(): Flow<List<RestaurantWithTransactionEntity>>

}