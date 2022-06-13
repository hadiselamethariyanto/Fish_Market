package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {

    fun insertCategory(categoryEntity: CategoryEntity): Flow<Resource<CategoryEntity>>

    fun updateCategory(categoryEntity: CategoryEntity): Flow<Resource<CategoryEntity>>

    fun deleteCategory(categoryEntity: CategoryEntity): Flow<Resource<List<CategoryEntity>>>

    fun getCategories(): Flow<Resource<List<CategoryEntity>>>

    fun getCategory(id: String): Flow<Resource<CategoryEntity>>
}