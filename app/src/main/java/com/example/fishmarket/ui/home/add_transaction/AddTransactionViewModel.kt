package com.example.fishmarket.ui.home.add_transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.domain.repository.IMenuRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.example.fishmarket.domain.usecase.category.CategoryUseCase
import com.example.fishmarket.domain.usecase.table.TableUseCase
import com.example.fishmarket.utilis.Utils

class AddTransactionViewModel(
    private val repository: ITransactionRepository,
    private val tableUseCase: TableUseCase,
    private val menuRepository: IMenuRepository,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private var _table = MutableLiveData<Table>()
    val table: LiveData<Table> get() = _table

    private var _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    fun setTable(table: Table) {
        _table.value = table
    }

    fun setRestaurant(restaurant: Restaurant) {
        _restaurant.value = restaurant
    }

    fun resetTransaction() {
        _table.value = Table("", "", false, 0)
        _restaurant.value = Restaurant("", "", 0)
    }

    fun addTransaction(
        totalFee: Int,
        detail: List<DetailTransactionResponse>
    ): LiveData<Resource<TransactionEntity>> {
        val id = Utils.getRandomString()
        val createdDate = System.currentTimeMillis()
        val status = 1
        val idTable = table.value?.id
        val idRestaurant = restaurant.value?.id

        val transaction = TransactionResponse(
            id = id,
            id_table = idTable ?: "",
            id_restaurant = idRestaurant ?: "",
            created_date = createdDate,
            dibakar_date = 0,
            disajikan_date = 0,
            status = status,
            finished_date = 0,
            total_fee = totalFee,
            detail = detail
        )

        return repository.addTransaction(transaction).asLiveData()
    }

    fun getMenus(id: String) = menuRepository.getMenusByCategory(id).asLiveData()

    fun getAvailableTable() = tableUseCase.getAvailableTable().asLiveData()

    fun getCategories() = categoryUseCase.getCategories().asLiveData()
}