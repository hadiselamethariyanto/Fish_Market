package com.example.fishmarket.ui.menu.list_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.domain.repository.IMenuRepository

class ListMenuViewModel(private val menuRepository: IMenuRepository) : ViewModel() {
    fun getMenus() = menuRepository.getMenus().asLiveData()

    fun deleteMenu(menuEntity: MenuEntity) = menuRepository.deleteMenu(menuEntity).asLiveData()
}