package com.example.fishmarket.ui.restaurant.edit_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository

class EditRestaurantViewModel(private val repository: IRestaurantRepository) : ViewModel() {

    fun getRestaurant(id: String) = repository.getRestaurant(id).asLiveData()

    fun updateRestaurant(restaurantEntity: RestaurantEntity) =
        repository.updateRestaurant(restaurantEntity).asLiveData()
}