package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {

    suspend fun addRestaurant(restaurant: RestaurantEntity): Long

    suspend fun deleteRestaurant(restaurant: RestaurantEntity)

    suspend fun updateRestaurant(restaurantEntity: RestaurantEntity): Int

    fun getRestaurant(): Flow<List<RestaurantEntity>>

    fun getRestaurant(id: String): Flow<RestaurantEntity>
}