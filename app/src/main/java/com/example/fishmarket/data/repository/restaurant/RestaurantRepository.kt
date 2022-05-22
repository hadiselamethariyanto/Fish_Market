package com.example.fishmarket.data.repository.restaurant

import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantRepository(private val localDataSource: RestaurantLocalDataSource) :
    IRestaurantRepository {

    override suspend fun addRestaurant(restaurant: RestaurantEntity) =
        localDataSource.addRestaurant(restaurant)

    override suspend fun deleteRestaurant(restaurant: RestaurantEntity) =
        localDataSource.deleteRestaurant(restaurant)

    override suspend fun updateRestaurant(restaurantEntity: RestaurantEntity) =
        localDataSource.updateRestaurant(restaurantEntity)

    override suspend fun getRestaurantWithTransaction(): List<RestaurantWithTransactionEntity> =
        localDataSource.getRestaurantWithTransaction()

    override fun getRestaurant(): Flow<List<RestaurantEntity>> = localDataSource.getRestaurant()

    override fun getRestaurant(id: String) = localDataSource.getRestaurant(id)

}