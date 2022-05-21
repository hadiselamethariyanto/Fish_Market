package com.example.fishmarket.ui.table.edit_table

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import kotlinx.coroutines.launch

class EditTableViewModel(private val repository: ITableRepository) : ViewModel() {

    private var _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int> get() = _isSuccess

    fun getTable(id: String) = repository.getTable(id).asLiveData()

    fun updateTable(table: TableEntity) = viewModelScope.launch {
        _isSuccess.value = repository.updateTable(table)
    }

}