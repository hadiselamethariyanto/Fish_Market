package com.example.fishmarket.domain.usecase.transaction

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.domain.model.Transaction
import com.example.fishmarket.domain.model.TransactionHome
import com.example.fishmarket.domain.model.TransactionWithDetail
import com.example.fishmarket.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionInteractor(private val repository: ITransactionRepository) : TransactionUseCase {

    override fun addTransaction(transaction: TransactionResponse): Flow<Resource<Transaction>> =
        repository.addTransaction(transaction)

    override fun changeStatusTransaction(transactionEntity: TransactionEntity): Flow<Resource<Transaction>> =
        repository.changeStatusTransaction(transactionEntity)

    override suspend fun setStatusTable(status: Boolean, id: String) =
        repository.setStatusTable(status, id)

    override fun getTransactions(filter: Int): Flow<Resource<List<TransactionHome>>> =
        repository.getTransactions(filter)

    override fun getTransactionWithDetail(): Flow<Resource<List<TransactionWithDetail>>> =
        repository.getTransactionWithDetail()

    override fun getRangeTransaction(first: Long, second: Long): Flow<Resource<List<Transaction>>> =
        repository.getRangeTransaction(first, second)

    override fun getDetailTransaction(id: String): Flow<List<DetailTransactionHistory>> =
        repository.getDetailTransaction(id)
}