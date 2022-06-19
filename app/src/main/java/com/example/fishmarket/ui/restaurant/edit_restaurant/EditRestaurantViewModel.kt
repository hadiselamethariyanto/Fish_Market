package com.example.fishmarket.ui.restaurant.edit_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.domain.usecase.restaurant.RestaurantUseCase
import com.example.fishmarket.ui.restaurant.add_restaurant.RestaurantFormState

class EditRestaurantViewModel(private val restaurantUseCase: RestaurantUseCase) : ViewModel() {

    private val _restaurantForm = MutableLiveData<RestaurantFormState>()
    val restaurantFormState: LiveData<RestaurantFormState> get() = _restaurantForm

    fun restaurantDataChanged(name: String) {
        if (!isRestaurantNameValid(name)) {
            _restaurantForm.value =
                RestaurantFormState(restaurantNameError = R.string.warning_restaurant_name_empty)
        } else {
            _restaurantForm.value = RestaurantFormState(isDataValid = true)
        }
    }

    private fun isRestaurantNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun getRestaurant(id: String) = restaurantUseCase.getRestaurant(id).asLiveData()

    fun updateRestaurant(restaurantEntity: RestaurantEntity) =
        restaurantUseCase.updateRestaurant(restaurantEntity).asLiveData()
}