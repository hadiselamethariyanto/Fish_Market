package com.example.fishmarket.data.repository.menu.source.local

import com.example.fishmarket.data.repository.menu.source.local.dao.MenuDao
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity

class MenuLocalDataSource(private val menuDao: MenuDao) {

    suspend fun insertMenu(menuEntity: MenuEntity) = menuDao.insertMenu(menuEntity)

    suspend fun insertMenus(list: List<MenuEntity>) = menuDao.insertMenus(list)

    fun getMenu(id: String) = menuDao.getMenu(id)

    fun getMenus() = menuDao.getMenus()
}