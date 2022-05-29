package com.example.fishmarket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.domain.repository.ITableRepository

class MainViewModel(
    private val tableRepository: ITableRepository,
    private val restaurantRepository: IRestaurantRepository,
    private val statusRepository: IStatusTransactionRepository
) : ViewModel() {

    fun getRestaurants() = restaurantRepository.getRestaurant().asLiveData()

    fun getTables() = tableRepository.getTables().asLiveData()

    fun getStatus() = statusRepository.getStatusTransaction().asLiveData()
}