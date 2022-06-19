package com.example.fishmarket.domain.usecase.menu

import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Menu
import com.example.fishmarket.domain.repository.IMenuRepository
import kotlinx.coroutines.flow.Flow

class MenuInteractor(private val repository: IMenuRepository) : MenuUseCase {
    override fun insertMenu(menu: MenuEntity): Flow<Resource<Menu>> = repository.insertMenu(menu)

    override fun editMenu(menu: MenuEntity): Flow<Resource<Menu>> = repository.editMenu(menu)

    override fun deleteMenu(menu: MenuEntity): Flow<Resource<List<Menu>>> =
        repository.deleteMenu(menu)

    override fun getMenus(): Flow<Resource<List<Menu>>> = repository.getMenus()

    override fun getMenusByCategory(id: String): Flow<Resource<List<Menu>>> =
        repository.getMenusByCategory(id)
}