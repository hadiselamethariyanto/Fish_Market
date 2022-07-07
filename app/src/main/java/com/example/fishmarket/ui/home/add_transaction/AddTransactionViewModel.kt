package com.example.fishmarket.ui.home.add_transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.domain.model.Transaction
import com.example.fishmarket.domain.model.TransactionWithDetail
import com.example.fishmarket.domain.usecase.category.CategoryUseCase
import com.example.fishmarket.domain.usecase.menu.MenuUseCase
import com.example.fishmarket.domain.usecase.table.TableUseCase
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase
import com.example.fishmarket.utilis.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val tableUseCase: TableUseCase,
    private val menuUseCase: MenuUseCase,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private var _table = MutableLiveData<Table>()
    val table: LiveData<Table> get() = _table

    private var _transactionWithDetail = MutableLiveData<TransactionWithDetail>()
    val transactionWithDetail: LiveData<TransactionWithDetail> get() = _transactionWithDetail

    fun setTable(table: Table) {
        _table.value = table
    }

    fun resetTransaction() {
        _table.value = Table("", "", false, "", 0)
        _transactionWithDetail.value = null
    }

    fun getTransactionWithDetail(id: String) = viewModelScope.launch {
        transactionUseCase.getTransactionWithDetail(id).collectLatest {
            _transactionWithDetail.value = it
        }
    }


    fun addTransaction(
        transactionResponse: TransactionResponse
    ): LiveData<Resource<Transaction>> {
        return transactionUseCase.addTransaction(transactionResponse).asLiveData()
    }

    fun getMenus(id: String) = menuUseCase.getMenusByCategory(id).asLiveData()

    fun getAvailableTable() = tableUseCase.getAvailableTable().asLiveData()

    fun getTables() = tableUseCase.getTables().asLiveData()

    fun getCategories() = categoryUseCase.getCategories().asLiveData()

    fun getQueueNumber() = transactionUseCase.getQueueNumber().asLiveData()

}