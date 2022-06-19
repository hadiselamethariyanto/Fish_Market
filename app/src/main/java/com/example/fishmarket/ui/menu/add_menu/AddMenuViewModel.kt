package com.example.fishmarket.ui.menu.add_menu

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.repository.ICategoryRepository
import com.example.fishmarket.domain.repository.IMenuRepository
import com.example.fishmarket.domain.usecase.category.CategoryUseCase
import com.example.fishmarket.domain.usecase.menu.MenuUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddMenuViewModel(
    private val menuUseCase: MenuUseCase,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

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

    fun getCategories() = categoryUseCase.getCategories().asLiveData()
}