package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val transactionRepository: ITransactionRepository,
    private val statusRepository: IStatusTransactionRepository,
    private val restaurantRepository: IRestaurantRepository
) : ViewModel() {

    private var _isSuccessUpdate = MutableLiveData<Int>()
    val isSuccessUpdate: LiveData<Int> get() = _isSuccessUpdate

    private var _filter = MutableLiveData(0)
    val filter: LiveData<Int> get() = _filter

    fun getTransactions() = transactionRepository.getTransactions().asLiveData()

    fun getStatusTransaction() = statusRepository.getStatusTransaction().asLiveData()

    fun changeStatusTransaction(
        transaction: TransactionHomeEntity,
        newStatus: Int,
        idRestaurant: Int
    ) =
        viewModelScope.launch {
            val dataTransactionUpdate = TransactionEntity(
                id = transaction.id,
                id_table = transaction.id_table,
                id_restaurant = transaction.id_restaurant,
                created_date = transaction.created_date,
                dibakar_date = transaction.dibakar_date,
                disajikan_date = transaction.disajikan_date,
                finished_date = transaction.finished_date,
                status = newStatus
            )

            val finishedDate = System.currentTimeMillis()
            when (newStatus) {
                4 -> {
                    dataTransactionUpdate.finished_date = finishedDate
                    transactionRepository.setStatusTable(false, transaction.id_table)
                }
                3 -> {
                    if (transaction.status > newStatus) {
                        dataTransactionUpdate.disajikan_date = 0
                    }

                    dataTransactionUpdate.disajikan_date = finishedDate
                    transactionRepository.setStatusTable(true, transaction.id_table)
                }
                2 -> {
                    if (transaction.status > newStatus) {
                        dataTransactionUpdate.finished_date = 0
                        dataTransactionUpdate.disajikan_date = 0
                    }

                    dataTransactionUpdate.dibakar_date = finishedDate
                    dataTransactionUpdate.id_restaurant = idRestaurant
                    transactionRepository.setStatusTable(true, transaction.id_table)
                }
                1 -> {
                    if (transaction.status > newStatus) {
                        dataTransactionUpdate.finished_date = 0
                        dataTransactionUpdate.disajikan_date = 0
                        dataTransactionUpdate.dibakar_date = 0
                    }
                    dataTransactionUpdate.created_date = finishedDate
                    transactionRepository.setStatusTable(true, transaction.id_table)
                }
            }
            _isSuccessUpdate.value =
                transactionRepository.changeStatusTransaction(dataTransactionUpdate)

        }

    fun changeFilter() {
        if (_filter.value == 0) {
            _filter.value = 1
        } else {
            _filter.value = 0
        }
    }

    fun getRestaurant() = restaurantRepository.getRestaurant().asLiveData()

    fun resetSuccessUpdate() {
        _isSuccessUpdate.value = 0
    }
}