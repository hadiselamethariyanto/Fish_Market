package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.*
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

    fun getTransactions() = transactionRepository.getTransactions().asLiveData()

    fun getStatusTransaction() = statusRepository.getStatusTransaction().asLiveData()

    fun changeStatusTransaction(id: Int, status: Int, idTable: Int, idRestaurant: Int) =
        viewModelScope.launch {
            _isSuccessUpdate.value = transactionRepository.changeStatusTransaction(id, status)
            val finishedDate = System.currentTimeMillis()
            when (status) {
                4 -> {
                    transactionRepository.setFinishedTransaction(id, finishedDate)
                    transactionRepository.setStatusTable(false, idTable)
                }
                2 -> {
                    transactionRepository.updateRestaurantTransaction(id, idRestaurant)
                    transactionRepository.setStatusTable(true, idTable)
                }
                else -> {
                    transactionRepository.setStatusTable(true, idTable)
                }
            }
        }

    fun getRestaurant() = restaurantRepository.getRestaurant().asLiveData()

    fun resetSuccessUpdate() {
        _isSuccessUpdate.value = 0
    }
}