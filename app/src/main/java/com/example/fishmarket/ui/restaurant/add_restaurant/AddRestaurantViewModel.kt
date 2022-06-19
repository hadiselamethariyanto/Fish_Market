package com.example.fishmarket.ui.restaurant.add_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.usecase.restaurant.RestaurantUseCase
import kotlinx.coroutines.launch

class AddRestaurantViewModel(private val restaurantUseCase: RestaurantUseCase) : ViewModel() {

    fun addRestaurant(restaurant: RestaurantEntity) =
        restaurantUseCase.addRestaurant(restaurant).asLiveData()

}