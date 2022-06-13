package com.example.fishmarket.data.repository.category.source.local

import com.example.fishmarket.data.repository.category.source.local.dao.CategoryDao
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity

class CategoryLocalDataSource(private val categoryDao: CategoryDao) {

    suspend fun addCategory(categoryEntity: CategoryEntity) =
        categoryDao.insertCategory(categoryEntity)

    suspend fun editCategory(categoryEntity: CategoryEntity) =
        categoryDao.updateCategory(categoryEntity)

    suspend fun deleteCategory(categoryEntity: CategoryEntity) =
        categoryDao.deleteCategory(categoryEntity)

    suspend fun addCategories(list: List<CategoryEntity>) = categoryDao.insertCategories(list)

    fun getCategory(id: String) = categoryDao.getCategory(id)

    fun getCategories() = categoryDao.getCategories()
}