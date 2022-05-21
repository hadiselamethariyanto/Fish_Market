package com.example.fishmarket.ui.table.add_table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import kotlinx.coroutines.launch

class AddTableViewModel(private val repository: ITableRepository) : ViewModel() {
    private var _isSuccess = MutableLiveData<Long>()
    val isSuccess: LiveData<Long> get() = _isSuccess

    fun addTable(table: TableEntity) = viewModelScope.launch {
        _isSuccess.value = repository.addTable(table)
    }
}