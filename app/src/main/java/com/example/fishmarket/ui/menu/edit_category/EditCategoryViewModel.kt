package com.example.fishmarket.ui.menu.edit_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.domain.usecase.category.CategoryUseCase
import com.example.fishmarket.ui.menu.add_category.CategoryFormState

class EditCategoryViewModel(private val categoryUseCase: CategoryUseCase) : ViewModel() {
    private val _categoryForm = MutableLiveData<CategoryFormState>()
    val categoryFormState: LiveData<CategoryFormState> get() = _categoryForm

    fun dataCategoryChanged(name: String) {
        if (!isCategoryNameValid(name)) {
            _categoryForm.value =
                CategoryFormState(categoryNameError = R.string.warning_category_name_empty)
        } else {
            _categoryForm.value = CategoryFormState(isDataValid = true)
        }
    }

    private fun isCategoryNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun editCategory(categoryEntity: CategoryEntity) =
        categoryUseCase.updateCategory(categoryEntity).asLiveData()
}