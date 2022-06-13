package com.example.fishmarket.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.ITransactionRepository

class ReportViewModel(private val transactionRepository: ITransactionRepository) : ViewModel() {

    fun getRangeTransaction(first: Long, second: Long) =
        transactionRepository.getRangeTransaction(first, second).asLiveData()
}