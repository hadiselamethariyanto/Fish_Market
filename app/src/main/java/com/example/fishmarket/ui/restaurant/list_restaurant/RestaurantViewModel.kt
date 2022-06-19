package com.example.fishmarket.ui.restaurant.list_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.usecase.restaurant.RestaurantUseCase
import kotlinx.coroutines.launch

class RestaurantViewModel(private val restaurantUseCase: RestaurantUseCase) : ViewModel() {

    fun getRestaurant() = restaurantUseCase.getRestaurant().asLiveData()

    fun getRestaurantWithTransaction() =
        restaurantUseCase.getRestaurantWithTransaction().asLiveData()

    fun deleteRestaurant(restaurantEntity: RestaurantEntity) =
        restaurantUseCase.deleteRestaurant(restaurantEntity).asLiveData()

}