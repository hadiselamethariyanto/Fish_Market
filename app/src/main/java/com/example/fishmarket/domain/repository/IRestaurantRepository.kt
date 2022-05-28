package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {

    fun addRestaurant(restaurant: RestaurantEntity): Flow<Resource<RestaurantEntity>>

    fun deleteRestaurant(restaurant: RestaurantEntity): Flow<Resource<List<RestaurantWithTransactionEntity>>>

    fun updateRestaurant(restaurantEntity: RestaurantEntity): Flow<Resource<RestaurantEntity>>

    fun getRestaurantWithTransaction(): Flow<List<RestaurantWithTransactionEntity>>

    fun getRestaurant(): Flow<List<RestaurantEntity>>

    fun getRestaurant(id: String): Flow<RestaurantEntity>
}