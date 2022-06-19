package com.example.fishmarket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.domain.usecase.login.LoginUseCase

class MainViewModel(
    private val statusRepository: IStatusTransactionRepository,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun getStatus() = statusRepository.getStatusTransaction().asLiveData()

    fun getCurrentUser() = loginUseCase.getCurrentUser().asLiveData()

}