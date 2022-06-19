package com.example.fishmarket.ui.menu.edit_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.usecase.category.CategoryUseCase

class EditCategoryViewModel(private val categoryUseCase: CategoryUseCase) : ViewModel() {
    fun editCategory(categoryEntity: CategoryEntity) =
        categoryUseCase.updateCategory(categoryEntity).asLiveData()
}