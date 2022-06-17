package com.example.fishmarket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.ILoginRepository
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.domain.repository.ITableRepository

class MainViewModel(
    private val statusRepository: IStatusTransactionRepository,
    private val loginRepository: ILoginRepository
) : ViewModel() {

    fun getStatus() = statusRepository.getStatusTransaction().asLiveData()

    fun getCurrentUser() = loginRepository.getCurrentUser().asLiveData()

}