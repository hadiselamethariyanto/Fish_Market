package com.example.fishmarket.ui.table.add_table

import androidx.lifecycle.*
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.usecase.table.TableUseCase

class AddTableViewModel(private val tableUseCase: TableUseCase) : ViewModel() {

    private val _tableForm = MutableLiveData<TableFormState>()
    val tableFormState: LiveData<TableFormState> = _tableForm

    fun dataTableChanged(name: String) {
        if (!isTableNameValid(name)) {
            _tableForm.value = TableFormState(tableNameError = R.string.not_valid_table_name)
        } else {
            _tableForm.value = TableFormState(isDataValid = true)
        }
    }

    private fun isTableNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun addTable(table: TableEntity) = tableUseCase.addTable(table).asLiveData()
}