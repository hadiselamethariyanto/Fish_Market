package com.example.fishmarket.ui.table.list_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import com.example.fishmarket.domain.usecase.TableUseCase
import kotlinx.coroutines.launch

class TableViewModel(private val tableUseCase: TableUseCase) : ViewModel() {

    fun getTables() = tableUseCase.getTables().asLiveData()

    fun deleteTable(table: TableEntity) = tableUseCase.deleteTable(table).asLiveData()

}