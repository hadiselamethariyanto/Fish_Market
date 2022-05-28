package com.example.fishmarket.ui.restaurant.add_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.launch

class AddRestaurantViewModel(private val repository: IRestaurantRepository) : ViewModel() {

    fun addRestaurant(restaurant: RestaurantEntity) = repository.addRestaurant(restaurant).asLiveData()


}