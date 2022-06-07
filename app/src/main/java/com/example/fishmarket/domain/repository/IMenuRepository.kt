package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

interface IMenuRepository {

    fun insertMenu(menu: MenuEntity): Flow<Resource<MenuEntity>>

    fun editMenu(menu: MenuEntity): Flow<Resource<MenuEntity>>

    fun deleteMenu(menu: MenuEntity): Flow<Resource<List<MenuEntity>>>

    fun getMenus(): Flow<Resource<List<MenuEntity>>>

}