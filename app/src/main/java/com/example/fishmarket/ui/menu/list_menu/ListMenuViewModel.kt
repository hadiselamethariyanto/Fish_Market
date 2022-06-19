package com.example.fishmarket.ui.menu.list_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.domain.repository.IMenuRepository
import com.example.fishmarket.domain.usecase.menu.MenuUseCase

class ListMenuViewModel(private val menuUseCase: MenuUseCase) : ViewModel() {
    fun getMenus() = menuUseCase.getMenus().asLiveData()

    fun deleteMenu(menuEntity: MenuEntity) = menuUseCase.deleteMenu(menuEntity).asLiveData()
}