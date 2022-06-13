package com.example.fishmarket.ui.menu.list_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.repository.ICategoryRepository

class ListCategoryViewModel(private val categoryRepository: ICategoryRepository) : ViewModel() {
    fun getCategories() = categoryRepository.getCategories().asLiveData()

    fun deleteCategory(categoryEntity: CategoryEntity) =
        categoryRepository.deleteCategory(categoryEntity).asLiveData()
}