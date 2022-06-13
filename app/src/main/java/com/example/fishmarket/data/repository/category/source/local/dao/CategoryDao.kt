package com.example.fishmarket.data.repository.category.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(list: List<CategoryEntity>)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category WHERE id=:id")
    fun getCategory(id: String): Flow<CategoryEntity>

    @Query("SELECT * FROM category ORDER BY name ASC")
    fun getCategories(): Flow<List<CategoryEntity>>
}