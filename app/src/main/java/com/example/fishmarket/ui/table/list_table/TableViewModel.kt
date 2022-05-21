package com.example.fishmarket.ui.table.list_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import kotlinx.coroutines.launch

class TableViewModel(private val repository: ITableRepository) : ViewModel() {

    fun getTables() = repository.getTables().asLiveData()

    fun deleteTable(table: TableEntity) = viewModelScope.launch {
        repository.deleteTable(table)
    }

}