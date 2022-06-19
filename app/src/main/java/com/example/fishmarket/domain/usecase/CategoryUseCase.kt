package com.example.fishmarket.domain.usecase

import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryUseCase {

    fun insertCategory(categoryEntity: CategoryEntity): Flow<Resource<Category>>

    fun updateCategory(categoryEntity: CategoryEntity): Flow<Resource<Category>>

    fun deleteCategory(categoryEntity: CategoryEntity): Flow<Resource<List<Category>>>

    fun getCategories(): Flow<Resource<List<Category>>>

    fun getCategory(id: String): Flow<Resource<Category>>
}