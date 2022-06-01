package com.example.fishmarket.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.domain.repository.IMenuRepository

class MenuViewModel(private val menuRepository: IMenuRepository) : ViewModel() {

    fun addMenu(menu: MenuEntity) = menuRepository.insertMenu(menu).asLiveData()

    fun getMenus() = menuRepository.getMenus().asLiveData()
}