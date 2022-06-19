package com.example.fishmarket.ui.table.edit_table

import androidx.lifecycle.*
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.domain.usecase.table.TableUseCase
import com.example.fishmarket.ui.table.add_table.TableFormState

class EditTableViewModel(private val tableUseCase: TableUseCase) : ViewModel() {

    private val _tableForm = MutableLiveData<TableFormState>()
    val tableFormState: LiveData<TableFormState> get() = _tableForm

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

    fun getTable(id: String) = tableUseCase.getTable(id).asLiveData()

    fun updateTable(table: TableEntity) = tableUseCase.updateTable(table).asLiveData()

}