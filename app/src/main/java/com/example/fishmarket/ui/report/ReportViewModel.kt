package com.example.fishmarket.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase

class ReportViewModel(private val transactionUseCase: TransactionUseCase) : ViewModel() {

    fun getRangeTransaction(first: Long, second: Long) =
        transactionUseCase.getRangeTransaction(first, second).asLiveData()
}