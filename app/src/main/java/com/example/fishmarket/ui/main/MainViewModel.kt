package com.example.fishmarket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.usecase.login.LoginUseCase
import com.example.fishmarket.domain.usecase.status_transaction.StatusTransactionUseCase

class MainViewModel(
    private val statusTransactionUseCase: StatusTransactionUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun getStatus() = statusTransactionUseCase.getStatusTransaction().asLiveData()

    fun getCurrentUser() = loginUseCase.getCurrentUser().asLiveData()

}