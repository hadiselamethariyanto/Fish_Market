package com.example.fishmarket.ui.restaurant.edit_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.usecase.restaurant.RestaurantUseCase

class EditRestaurantViewModel(private val restaurantUseCase: RestaurantUseCase) : ViewModel() {

    fun getRestaurant(id: String) = restaurantUseCase.getRestaurant(id).asLiveData()

    fun updateRestaurant(restaurantEntity: RestaurantEntity) =
        restaurantUseCase.updateRestaurant(restaurantEntity).asLiveData()
}