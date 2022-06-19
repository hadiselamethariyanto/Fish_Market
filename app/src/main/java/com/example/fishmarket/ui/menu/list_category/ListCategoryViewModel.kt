package com.example.fishmarket.ui.menu.list_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.usecase.CategoryUseCase

class ListCategoryViewModel(private val categoryUseCase: CategoryUseCase) : ViewModel() {
    fun getCategories() = categoryUseCase.getCategories().asLiveData()

    fun deleteCategory(categoryEntity: CategoryEntity) =
        categoryUseCase.deleteCategory(categoryEntity).asLiveData()
}