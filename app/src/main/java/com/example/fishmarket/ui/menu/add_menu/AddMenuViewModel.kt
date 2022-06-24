package com.example.fishmarket.ui.menu.add_menu

import androidx.lifecycle.*
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.usecase.category.CategoryUseCase
import com.example.fishmarket.domain.usecase.menu.MenuUseCase
import kotlinx.coroutines.launch

class AddMenuViewModel(
    private val menuUseCase: MenuUseCase,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _menuForm = MutableLiveData<MenuFormState>()
    val menuFormState: LiveData<MenuFormState> get() = _menuForm

    private val _categoryId = MutableLiveData<String>()
    val categoryId: LiveData<String> get() = _categoryId

    private val _categoryName = MutableLiveData<String>()
    val categoryName: LiveData<String> get() = _categoryName

    fun insertMenu(menu: MenuEntity) = menuUseCase.insertMenu(menu).asLiveData()

    fun editMenu(menuEntity: MenuEntity) = menuUseCase.editMenu(menuEntity).asLiveData()

    fun getCategory(id: String) = viewModelScope.launch {
        categoryUseCase.getCategory(id).collect { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (res.data != null) {
                        _categoryId.value = res.data.id
                        _categoryName.value = res.data.name
                    }
                }
                is Resource.Error -> {

                }
            }

        }
    }

    fun setCategoryId(value: String) {
        _categoryId.value = value
    }

    fun setCategoryName(value: String) {
        _categoryName.value = value
    }

    fun menuDataChanged(name: String, price: String, category: String, unit: String) {
        if (!isTextFieldValid(name)) {
            _menuForm.value = MenuFormState(nameError = R.string.warning_menu_name_empty)
        } else if (!isTextFieldValid(price)) {
            _menuForm.value = MenuFormState(priceError = R.string.warning_price_menu_empty)
        } else if (!isTextFieldValid(category)) {
            _menuForm.value = MenuFormState(categoryError = R.string.warning_category_menu_empty)
        } else if (!isTextFieldValid(unit)) {
            _menuForm.value = MenuFormState(unit = R.string.warning_unit_menu_empty)
        } else {
            _menuForm.value = MenuFormState(isDataValid = true)
        }
    }

    private fun isTextFieldValid(value: String): Boolean {
        return value.isNotEmpty()
    }

    fun getCategories() = categoryUseCase.getCategories().asLiveData()
}