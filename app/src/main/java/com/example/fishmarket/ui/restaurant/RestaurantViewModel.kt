package com.example.fishmarket.ui.restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.launch

class RestaurantViewModel(private val repository: IRestaurantRepository) : ViewModel() {

    fun getRestaurant() = repository.getRestaurant().asLiveData()

    fun deleteRestaurant(restaurantEntity: RestaurantEntity) = viewModelScope.launch {
        repository.deleteRestaurant(restaurantEntity)
    }

}