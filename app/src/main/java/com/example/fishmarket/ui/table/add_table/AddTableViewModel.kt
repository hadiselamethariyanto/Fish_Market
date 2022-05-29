package com.example.fishmarket.ui.table.add_table

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository

class AddTableViewModel(private val repository: ITableRepository) : ViewModel() {

    fun addTable(table: TableEntity) = repository.addTable(table).asLiveData()
}