package com.example.fishmarket.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase

class ReportViewModel(private val transactionUseCase: TransactionUseCase) : ViewModel() {

    private val _dateRangeState = MutableLiveData<DateRangeFormState>()
    val dateRangeFormState: LiveData<DateRangeFormState> get() = _dateRangeState

    fun dateRangeChanged(first: Long, second: Long) {
        _dateRangeState.value = DateRangeFormState(first = first, second = second)
    }

    fun getRangeTransaction(first: Long, second: Long) =
        transactionUseCase.getRangeTransaction(first, second).asLiveData()

    fun getDetailTransactionRestaurant(idRestaurant: String, first: Long, second: Long) =
        transactionUseCase.getDetailTransactionRestaurant(idRestaurant, first, second).asLiveData()
}