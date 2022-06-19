package com.example.fishmarket.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.usecase.login.LoginUseCase

class SettingsViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    fun logout() = loginUseCase.logout().asLiveData()

    fun getCurrentUser() = loginUseCase.getCurrentUser().asLiveData()
}