package com.example.fishmarket.ui.restaurant.list_restaurant

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.launch

class RestaurantViewModel(private val repository: IRestaurantRepository) : ViewModel() {

//    private var _restaurantWithTransaction =
//        MutableLiveData<List<RestaurantWithTransactionEntity>>()
//    val restaurantWithTransaction: LiveData<List<RestaurantWithTransactionEntity>> get() = _restaurantWithTransaction

    fun getRestaurant() = repository.getRestaurant().asLiveData()

    fun getRestaurantWithTransaction() = repository.getRestaurantWithTransaction().asLiveData()

    fun deleteRestaurant(restaurantEntity: RestaurantEntity) =
        repository.deleteRestaurant(restaurantEntity).asLiveData()

}