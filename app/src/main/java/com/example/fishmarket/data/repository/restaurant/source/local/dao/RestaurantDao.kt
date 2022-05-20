package com.example.fishmarket.data.repository.restaurant.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: RestaurantEntity): Long

    @Delete
    suspend fun deleteRestaurant(restaurant: RestaurantEntity)

    @Update
    suspend fun updateRestaurant(restaurant: RestaurantEntity): Int

    @Query("SELECT * FROM restaurant ORDER BY id DESC")
    fun getRestaurant(): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM restaurant WHERE id=:id")
    fun getRestaurant(id: String): Flow<RestaurantEntity>

}