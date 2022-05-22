package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.*
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val transactionRepository: ITransactionRepository,
    private val statusRepository: IStatusTransactionRepository
) : ViewModel() {

    private var _isSuccessUpdate = MutableLiveData<Int>()
    val isSuccessUpdate: LiveData<Int> get() = _isSuccessUpdate

    fun getTransactions() = transactionRepository.getTransactions().asLiveData()

    fun getStatusTransaction() = statusRepository.getStatusTransaction().asLiveData()

    fun changeStatusTransaction(id: Int, status: Int, idTable: Int) = viewModelScope.launch {
        _isSuccessUpdate.value = transactionRepository.changeStatusTransaction(id, status)
        val finishedDate = System.currentTimeMillis()
        if (status == 3) {
            transactionRepository.setFinishedTransaction(id, finishedDate)
            transactionRepository.setStatusTable(false, idTable)
        } else {
            transactionRepository.setStatusTable(true, idTable)
        }
    }

    fun resetSuccessUpdate() {
        _isSuccessUpdate.value = 0
    }
}