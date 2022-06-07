package com.example.fishmarket.data.repository.menu.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenus(list: List<MenuEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: MenuEntity)

    @Update
    suspend fun updateMenu(menu: MenuEntity)

    @Delete
    suspend fun deleteMenu(menu: MenuEntity)

    @Query("SELECT * FROM menu WHERE id=:id")
    fun getMenu(id: String): Flow<MenuEntity>

    @Query("SELECT * FROM menu ORDER BY name ASC")
    fun getMenus(): Flow<List<MenuEntity>>
}