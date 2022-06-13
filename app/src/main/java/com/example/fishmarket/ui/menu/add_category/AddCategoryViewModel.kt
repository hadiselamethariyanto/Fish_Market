package com.example.fishmarket.ui.menu.add_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.repository.ICategoryRepository

class AddCategoryViewModel(private val categoryRepository: ICategoryRepository) : ViewModel() {

    fun addCategory(categoryEntity: CategoryEntity) =
        categoryRepository.insertCategory(categoryEntity).asLiveData()
}