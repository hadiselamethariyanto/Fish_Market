package com.example.fishmarket.ui.menu.edit_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.domain.repository.IMenuRepository

class EditMenuViewModel(private val menuRepository: IMenuRepository) : ViewModel() {

    fun editMenu(menuEntity: MenuEntity) = menuRepository.editMenu(menuEntity).asLiveData()

}