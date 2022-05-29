package com.example.fishmarket.data.repository.status_transaction.source.remote

import com.example.fishmarket.data.repository.status_transaction.source.remote.model.StatusTransactionResponse
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class StatusTransactionRemoteDataSource(private val firebaseFirestore: FirebaseFirestore) {

    fun getStatusTransaction() = flow<ApiResponse<List<StatusTransactionResponse>>> {
        val statusTransactionReference =
            firebaseFirestore.collection("status_transaction").get().await()
        val statusTransaction =
            statusTransactionReference.toObjects(StatusTransactionResponse::class.java)
        emit(ApiResponse.Success(statusTransaction))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}