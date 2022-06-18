package com.example.fishmarket.ui.table.add_table

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.repository.ITableRepository
import com.example.fishmarket.domain.usecase.TableUseCase

class AddTableViewModel(private val tableUseCase: TableUseCase) : ViewModel() {

    fun addTable(table: TableEntity) = tableUseCase.addTable(table).asLiveData()
}