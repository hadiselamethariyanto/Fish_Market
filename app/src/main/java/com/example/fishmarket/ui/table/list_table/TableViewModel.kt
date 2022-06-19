package com.example.fishmarket.ui.table.list_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.usecase.table.TableUseCase

class TableViewModel(private val tableUseCase: TableUseCase) : ViewModel() {

    fun getTables() = tableUseCase.getTables().asLiveData()

    fun deleteTable(table: TableEntity) = tableUseCase.deleteTable(table).asLiveData()

}