package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.ITransactionRepository

class HomeViewModel(private val repository: ITransactionRepository) : ViewModel() {

    fun getTransactions() = repository.getTransactions().asLiveData()
}