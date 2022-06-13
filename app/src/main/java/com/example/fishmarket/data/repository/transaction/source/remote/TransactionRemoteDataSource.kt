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
import java.util.*

class TransactionRemoteDataSource(private val firebase: FirebaseFirestore) {
    suspend fun addTransaction(transaction: TransactionResponse) =
        flow<ApiResponse<TransactionResponse>> {
            val transactionReference =
                firebase.collection("transaction").document(transaction.id ?: "")
            transactionReference.set(transaction).await()
            emit(ApiResponse.Success(transaction))
        }.catch {
            emit(ApiResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    suspend fun updateTransaction(transaction: TransactionEntity) =
        flow<ApiResponse<TransactionEntity>> {
            val dataUpdate = mapOf<String, Any?>(
                "id_restaurant" to transaction.id_restaurant,
                "created_date" to transaction.created_date,
                "dibakar_date" to transaction.dibakar_date,
                "disajikan_date" to transaction.disajikan_date,
                "finished_date" to transaction.finished_date,
                "status" to transaction.status
            )
            val transactionReference = firebase.collection("transaction").document(transaction.id)
            transactionReference.update(dataUpdate).await()
            emit(ApiResponse.Success(transaction))
        }.catch {
            emit(ApiResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    suspend fun getTransactions() = flow<ApiResponse<List<TransactionResponse>>> {
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val transactionReference =
            firebase.collection("transaction")
                .whereGreaterThanOrEqualTo("created_date", cal.timeInMillis).get().await()
        val transactions = transactionReference.toObjects(TransactionResponse::class.java)
        emit(ApiResponse.Success(transactions))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun getLastFiftyTransactions() = flow<ApiResponse<List<TransactionResponse>>> {
        val transactionReference =
            firebase.collection("transaction").orderBy("created_date", Query.Direction.DESCENDING)
                .limit(50)
                .get().await()
        val transactions = transactionReference.toObjects(TransactionResponse::class.java)
        emit(ApiResponse.Success(transactions))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun getRangeTransaction(first: Long, second: Long) =
        flow<ApiResponse<List<TransactionResponse>>> {
            val transactionReference =
                firebase.collection("transaction")
                    .whereGreaterThanOrEqualTo("created_date", first)
                    .whereLessThanOrEqualTo("created_date", second).get().await()
            val transactions = transactionReference.toObjects(TransactionResponse::class.java)
            emit(ApiResponse.Success(transactions))
        }.catch {
            emit(ApiResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}