package com.example.fishmarket.utilis

import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.remote.model.RestaurantResponse

object DataMapper {

    fun mapRestaurantResponseToEntity(list: List<RestaurantResponse>): List<RestaurantEntity> =
        list.map {
            RestaurantEntity(
                id = it.id ?: "",
                name = it.name ?: "",
                createdDate = it.createdDate ?: 0
            )
        }
}