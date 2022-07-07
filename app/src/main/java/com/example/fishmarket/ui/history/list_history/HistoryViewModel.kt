package com.example.fishmarket.ui.history.list_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase

class HistoryViewModel(private val transactionUseCase: TransactionUseCase) : ViewModel() {
    fun getTransactionsWithDetail() = transactionUseCase.getTransactionsWithDetail().asLiveData()
}