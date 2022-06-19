package com.example.fishmarket.domain.usecase.restaurant

import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.domain.model.RestaurantWithTransaction
import kotlinx.coroutines.flow.Flow

interface RestaurantUseCase {
    fun addRestaurant(restaurant: RestaurantEntity): Flow<Resource<Restaurant>>

    fun deleteRestaurant(restaurant: RestaurantEntity): Flow<Resource<List<RestaurantWithTransaction>>>

    fun updateRestaurant(restaurantEntity: RestaurantEntity): Flow<Resource<Restaurant>>

    fun getRestaurantWithTransaction(): Flow<Resource<List<RestaurantWithTransaction>>>

    fun getRestaurant(): Flow<Resource<List<Restaurant>>>

    fun getRestaurant(id: String): Flow<Restaurant>
}