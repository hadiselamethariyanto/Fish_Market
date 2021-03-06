package com.example.fishmarket.data.repository.table.source.remote

import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.table.source.remote.model.TableResponse
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class TableRemoteDataSource(private val firebase: FirebaseFirestore) {
    fun addTable(tableEntity: TableEntity) = flow<ApiResponse<TableEntity>> {
        val tableReference = firebase.collection("table").document(tableEntity.id)
        tableReference.set(tableEntity).await()
        emit(ApiResponse.Success(tableEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateTable(tableEntity: TableEntity) = flow<ApiResponse<TableEntity>> {
        val tableReference = firebase.collection("table").document(tableEntity.id)
        tableReference.set(tableEntity).await()
        emit(ApiResponse.Success(tableEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun deleteTable(tableEntity: TableEntity) = flow<ApiResponse<TableEntity>> {
        val tableReference = firebase.collection("table").document(tableEntity.id)
        tableReference.delete().await()
        emit(ApiResponse.Success(tableEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTables() = flow<ApiResponse<List<TableResponse>>> {
        val tableReference = firebase.collection("table").get().await()
        val table = tableReference.toObjects(TableResponse::class.java)
        emit(ApiResponse.Success(table))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}