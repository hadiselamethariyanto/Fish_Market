package com.example.fishmarket.domain.usecase.category

import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Category
import com.example.fishmarket.domain.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryInteractor(private val repository: ICategoryRepository) : CategoryUseCase {
    override fun insertCategory(categoryEntity: CategoryEntity): Flow<Resource<Category>> =
        repository.insertCategory(categoryEntity)

    override fun updateCategory(categoryEntity: CategoryEntity): Flow<Resource<Category>> =
        repository.updateCategory(categoryEntity)

    override fun deleteCategory(categoryEntity: CategoryEntity): Flow<Resource<List<Category>>> =
        repository.deleteCategory(categoryEntity)

    override fun getCategories(): Flow<Resource<List<Category>>> = repository.getCategories()

    override fun getCategory(id: String): Flow<Resource<Category>> = repository.getCategory(id)

}