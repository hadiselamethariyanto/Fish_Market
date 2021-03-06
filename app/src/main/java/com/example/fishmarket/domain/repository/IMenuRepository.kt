package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Menu
import kotlinx.coroutines.flow.Flow

interface IMenuRepository {

    fun insertMenu(menu: MenuEntity): Flow<Resource<Menu>>

    fun editMenu(menu: MenuEntity): Flow<Resource<Menu>>

    fun deleteMenu(menu: MenuEntity): Flow<Resource<List<Menu>>>

    fun getMenus(): Flow<Resource<List<Menu>>>

    fun getMenusByCategory(id: String): Flow<Resource<List<Menu>>>


}