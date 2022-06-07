package com.example.fishmarket.ui.menu.add_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.domain.repository.IMenuRepository

class AddMenuViewModel(private val menuRepository: IMenuRepository) : ViewModel() {

    fun insertMenu(menu: MenuEntity) = menuRepository.insertMenu(menu).asLiveData()

}