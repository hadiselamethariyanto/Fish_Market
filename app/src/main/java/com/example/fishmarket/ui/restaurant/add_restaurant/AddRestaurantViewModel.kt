package com.example.fishmarket.ui.restaurant.add_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.launch

class AddRestaurantViewModel(private val repository: IRestaurantRepository) : ViewModel() {

    private var _isSuccess = MutableLiveData<Long>()
    val isSuccess: LiveData<Long> get() = _isSuccess

    fun addRestaurant(restaurant: RestaurantEntity) {
        viewModelScope.launch {
            _isSuccess.value = repository.addRestaurant(restaurant)

        }
    }


}