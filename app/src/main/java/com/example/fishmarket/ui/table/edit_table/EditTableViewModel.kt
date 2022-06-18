package com.example.fishmarket.ui.table.edit_table

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import com.example.fishmarket.domain.usecase.TableUseCase

class EditTableViewModel(private val tableUseCase: TableUseCase) : ViewModel() {

    fun getTable(id: String) = tableUseCase.getTable(id).asLiveData()

    fun updateTable(table: TableEntity) = tableUseCase.updateTable(table).asLiveData()

}