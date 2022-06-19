package com.example.fishmarket.ui.menu.add_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.usecase.category.CategoryUseCase

class AddCategoryViewModel(private val categoryUseCase: CategoryUseCase) : ViewModel() {

    fun addCategory(categoryEntity: CategoryEntity) =
        categoryUseCase.insertCategory(categoryEntity).asLiveData()
}