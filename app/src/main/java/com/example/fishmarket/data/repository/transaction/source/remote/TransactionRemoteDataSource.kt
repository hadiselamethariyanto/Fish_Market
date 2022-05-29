package com.example.fishmarket.data.repository.transaction.source.remote

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class TransactionRemoteDataSource(private val firebase: FirebaseFirestore) {
    fun addTransaction(transaction: TransactionEntity) = flow<ApiResponse<TransactionEntity>> {
        val transactionReference = firebase.collection("transaction").document(transaction.id)
        transactionReference.set(transaction).await()
        emit(ApiResponse.Success(transaction))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateTransaction(transaction: TransactionEntity) = flow<ApiResponse<TransactionEntity>> {
        val transactionReference = firebase.collection("transaction").document(transaction.id)
        transactionReference.set(transaction).await()
        emit(ApiResponse.Success(transaction))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTransactions() = flow<ApiResponse<List<TransactionResponse>>> {
        val transactionReference =
            firebase.collection("transaction").orderBy("created_date", Query.Direction.DESCENDING)
                .limitToLast(100).get().await()
        val transactions = transactionReference.toObjects(TransactionResponse::class.java)
        emit(ApiResponse.Success(transactions))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}