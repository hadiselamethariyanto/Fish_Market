package com.example.fishmarket.ui.home.add_transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.domain.model.Transaction
import com.example.fishmarket.domain.usecase.category.CategoryUseCase
import com.example.fishmarket.domain.usecase.menu.MenuUseCase
import com.example.fishmarket.domain.usecase.table.TableUseCase
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase
import com.example.fishmarket.utilis.Utils

class AddTransactionViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val tableUseCase: TableUseCase,
    private val menuUseCase: MenuUseCase,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private var _table = MutableLiveData<Table>()
    val table: LiveData<Table> get() = _table

    fun setTable(table: Table) {
        _table.value = table
    }

    fun resetTransaction() {
        _table.value = Table("", "", false, 0)
    }

    fun addTransaction(
        totalFee: Int,
        queue:Int,
        detail: List<DetailTransactionResponse>
    ): LiveData<Resource<Transaction>> {
        val id = Utils.getRandomString()
        val createdDate = System.currentTimeMillis()
        val status = 1
        val idTable = table.value?.id

        val transaction = TransactionResponse(
            id = id,
            id_table = idTable ?: "",
            id_restaurant = "",
            created_date = createdDate,
            dibakar_date = 0,
            disajikan_date = 0,
            status = status,
            finished_date = 0,
            total_fee = totalFee,
            detail = detail,
            no_urut = queue
        )

        return transactionUseCase.addTransaction(transaction).asLiveData()
    }

    fun getMenus(id: String) = menuUseCase.getMenusByCategory(id).asLiveData()

    fun getAvailableTable() = tableUseCase.getAvailableTable().asLiveData()

    fun getTables() = tableUseCase.getTables().asLiveData()

    fun getCategories() = categoryUseCase.getCategories().asLiveData()

    fun getQueueNumber() = transactionUseCase.getQueueNumber().asLiveData()
}