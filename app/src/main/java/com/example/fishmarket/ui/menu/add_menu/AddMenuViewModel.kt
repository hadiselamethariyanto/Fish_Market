package com.example.fishmarket.ui.menu.add_menu

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.repository.ICategoryRepository
import com.example.fishmarket.domain.repository.IMenuRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddMenuViewModel(
    private val menuRepository: IMenuRepository,
    private val categoryRepository: ICategoryRepository
) : ViewModel() {

    private val _categoryId = MutableLiveData<String>()
    val categoryId: LiveData<String> get() = _categoryId

    private val _categoryName = MutableLiveData<String>()
    val categoryName: LiveData<String> get() = _categoryName

    fun insertMenu(menu: MenuEntity) = menuRepository.insertMenu(menu).asLiveData()

    fun editMenu(menuEntity: MenuEntity) = menuRepository.editMenu(menuEntity).asLiveData()

    fun getCategory(id: String) = viewModelScope.launch {
        categoryRepository.getCategory(id).collect { res ->
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

    fun getCategories() = categoryRepository.getCategories().asLiveData()
}