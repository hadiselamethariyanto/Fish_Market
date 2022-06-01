package com.example.fishmarket.data.repository.menu.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenus(list: List<MenuEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: MenuEntity)

    @Query("SELECT * FROM menu WHERE id=:id")
    fun getMenu(id: String): Flow<MenuEntity>

    @Query("SELECT * FROM menu ORDER BY name ASC")
    fun getMenus(): Flow<List<MenuEntity>>
}