package com.example.fishmarket.ui.table.edit_table

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository

class EditTableViewModel(private val repository: ITableRepository) : ViewModel() {

    fun getTable(id: String) = repository.getTable(id).asLiveData()

    fun updateTable(table: TableEntity) = repository.updateTable(table).asLiveData()

}