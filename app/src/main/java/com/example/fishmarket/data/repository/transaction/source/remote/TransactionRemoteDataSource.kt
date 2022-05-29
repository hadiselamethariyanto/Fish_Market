package com.example.fishmarket.data.repository.transaction.source.remote

import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
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
}