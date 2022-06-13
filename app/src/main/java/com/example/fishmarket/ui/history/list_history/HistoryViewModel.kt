package com.example.fishmarket.ui.history.list_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.ITransactionRepository

class HistoryViewModel(private val transactionRepository: ITransactionRepository) : ViewModel() {
    fun getTransactionsWithDetail() = transactionRepository.getTransactionWithDetail().asLiveData()
}