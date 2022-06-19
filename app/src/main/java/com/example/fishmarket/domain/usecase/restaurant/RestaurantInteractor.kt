package com.example.fishmarket.domain.usecase.restaurant

import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.domain.model.RestaurantWithTransaction
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantInteractor(private val repository: IRestaurantRepository) : RestaurantUseCase {
    override fun addRestaurant(restaurant: RestaurantEntity): Flow<Resource<Restaurant>> =
        repository.addRestaurant(restaurant)

    override fun deleteRestaurant(restaurant: RestaurantEntity): Flow<Resource<List<RestaurantWithTransaction>>> =
        repository.deleteRestaurant(restaurant)

    override fun updateRestaurant(restaurantEntity: RestaurantEntity): Flow<Resource<Restaurant>> =
        repository.updateRestaurant(restaurantEntity)

    override fun getRestaurantWithTransaction(): Flow<Resource<List<RestaurantWithTransaction>>> =
        repository.getRestaurantWithTransaction()

    override fun getRestaurant(): Flow<Resource<List<Restaurant>>> = repository.getRestaurant()

    override fun getRestaurant(id: String): Flow<Restaurant> = repository.getRestaurant(id)
}