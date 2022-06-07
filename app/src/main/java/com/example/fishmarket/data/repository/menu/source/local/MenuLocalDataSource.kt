package com.example.fishmarket.data.repository.menu.source.local

import com.example.fishmarket.data.repository.menu.source.local.dao.MenuDao
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity

class MenuLocalDataSource(private val menuDao: MenuDao) {

    suspend fun insertMenu(menuEntity: MenuEntity) = menuDao.insertMenu(menuEntity)

    suspend fun insertMenus(list: List<MenuEntity>) = menuDao.insertMenus(list)

    suspend fun updateMenu(menuEntity: MenuEntity) = menuDao.updateMenu(menuEntity)

    suspend fun deleteMenu(menuEntity: MenuEntity) = menuDao.deleteMenu(menuEntity)

    fun getMenu(id: String) = menuDao.getMenu(id)

    fun getMenus() = menuDao.getMenus()
}