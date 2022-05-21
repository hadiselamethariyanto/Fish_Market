package com.example.fishmarket.ui.restaurant.edit_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.launch

class EditRestaurantViewModel(private val repository: IRestaurantRepository) : ViewModel() {

    private var _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> get() = _isSuccess

    fun getRestaurant(id: String) = repository.getRestaurant(id).asLiveData()

    fun updateRestaurant(restaurantEntity: RestaurantEntity) = viewModelScope.launch {
        _isSuccess.value = repository.updateRestaurant(restaurantEntity)
    }
}