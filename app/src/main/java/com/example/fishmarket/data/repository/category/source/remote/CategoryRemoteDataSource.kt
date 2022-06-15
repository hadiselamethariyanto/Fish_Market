package com.example.fishmarket.data.repository.category.source.remote

import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.repository.category.source.remote.model.CategoryResponse
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class CategoryRemoteDataSource(private val firebaseFirestore: FirebaseFirestore) {
    suspend fun addCategory(categoryEntity: CategoryEntity) = flow<ApiResponse<CategoryEntity>> {
        val categoryReference = firebaseFirestore.collection("category").document(categoryEntity.id)
        categoryReference.set(categoryEntity).await()
        emit(ApiResponse.Success(categoryEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun updateCategory(categoryEntity: CategoryEntity) = flow<ApiResponse<CategoryEntity>> {
        val categoryReference = firebaseFirestore.collection("category").document(categoryEntity.id)
        categoryReference.set(categoryEntity).await()
        emit(ApiResponse.Success(categoryEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun deleteCategory(categoryEntity: CategoryEntity) = flow<ApiResponse<CategoryEntity>> {
        firebaseFirestore.collection("category").document(categoryEntity.id).delete().await()
        emit(ApiResponse.Success(categoryEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun getCategories() = flow<ApiResponse<List<CategoryResponse>>> {
        val categoryReference = firebaseFirestore.collection("category").get().await()
        val category = categoryReference.toObjects(CategoryResponse::class.java)
        emit(ApiResponse.Success(category))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}