package com.example.fishmarket.data.repository.restaurant.source.local

import com.example.fishmarket.data.repository.restaurant.source.local.dao.RestaurantDao
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity

class RestaurantLocalDataSource(private val restaurantDao: RestaurantDao) {

    suspend fun addRestaurant(restaurant: RestaurantEntity) =
        restaurantDao.insertRestaurant(restaurant)

    suspend fun deleteRestaurant(restaurant: RestaurantEntity) =
        restaurantDao.deleteRestaurant(restaurant)

    suspend fun updateRestaurant(restaurant: RestaurantEntity) =
        restaurantDao.updateRestaurant(restaurant)

    fun getRestaurant() = restaurantDao.getRestaurant()

    fun getRestaurant(id: String) = restaurantDao.getRestaurant(id)

}