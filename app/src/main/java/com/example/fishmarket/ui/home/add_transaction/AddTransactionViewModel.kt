package com.example.fishmarket.ui.home.add_transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.domain.repository.ITableRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val repository: ITransactionRepository,
    private val tableRepository: ITableRepository
) : ViewModel() {

    private var _isSuccess = MutableLiveData<Long>()
    val isSuccess: LiveData<Long> get() = _isSuccess

    private var _table = MutableLiveData<TableEntity>()
    val table: LiveData<TableEntity> get() = _table

    private var _restaurant = MutableLiveData<RestaurantEntity>()
    val restaurant: LiveData<RestaurantEntity> get() = _restaurant

    fun setTable(table: TableEntity) {
        _table.value = table
    }

    fun setRestaurant(restaurant: RestaurantEntity) {
        _restaurant.value = restaurant
    }

    fun resetIsSuccess() {
        _isSuccess.value = 0
    }

    fun addTransaction() {
        val createdDate = System.currentTimeMillis()
        val status = 1
        val idTable = table.value?.id
        val idRestaurant = restaurant.value?.id

        val transaction = TransactionEntity(
            id = 0,
            id_table = idTable ?: 0,
            id_restaurant = idRestaurant ?: 0,
            created_date = createdDate,
            dibakar_date = 0,
            disajikan_date = 0,
            status = status,
            finished_date = 0
        )

        viewModelScope.launch {
            _isSuccess.value = repository.addTransaction(transaction)
            repository.setStatusTable(true, idTable ?: 0)
        }

    }

    fun getAvailableTable() = tableRepository.getAvailableTable().asLiveData()
}