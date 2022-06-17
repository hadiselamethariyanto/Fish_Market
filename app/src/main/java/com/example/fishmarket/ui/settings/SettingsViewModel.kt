package com.example.fishmarket.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.ILoginRepository

class SettingsViewModel(private val loginRepository: ILoginRepository) : ViewModel() {
    fun logout() = loginRepository.logout().asLiveData()
}