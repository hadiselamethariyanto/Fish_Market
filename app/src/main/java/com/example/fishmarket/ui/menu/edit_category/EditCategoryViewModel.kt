package com.example.fishmarket.ui.menu.edit_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.repository.ICategoryRepository

class EditCategoryViewModel(private val categoryRepository: ICategoryRepository) : ViewModel() {
    fun editCategory(categoryEntity: CategoryEntity) =
        categoryRepository.updateCategory(categoryEntity).asLiveData()
}