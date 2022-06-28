package com.example.fishmarket.ui.report

import androidx.lifecycle.*
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase
import kotlinx.coroutines.launch

class ReportViewModel(private val transactionUseCase: TransactionUseCase) : ViewModel() {

    private val _dateRangeState = MutableLiveData<DateRangeFormState>()
    val dateRangeFormState: LiveData<DateRangeFormState> get() = _dateRangeState

    private val _detailTransactionHistory = MutableLiveData<List<DetailTransactionHistory>>()
    val detailTransactionHistory: LiveData<List<DetailTransactionHistory>> get() = _detailTransactionHistory

    fun dateRangeChanged(first: Long, second: Long) {
        _dateRangeState.value = DateRangeFormState(first = first, second = second)
    }

    fun getRangeTransaction(first: Long, second: Long) =
        transactionUseCase.getRangeTransaction(first, second).asLiveData()

    fun getDetailTransactionRestaurant(idRestaurant: String, first: Long, second: Long) =
        viewModelScope.launch {
            transactionUseCase.getDetailTransactionRestaurant(idRestaurant, first, second).collect {
                _detailTransactionHistory.value = it
            }
        }
}