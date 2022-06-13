package com.example.fishmarket.data.repository.menu.source.remote

import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.repository.menu.source.remote.model.MenuResponse
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class MenuRemoteDataSource(private val firebaseFirestore: FirebaseFirestore) {
    suspend fun addMenu(menu: MenuEntity) = flow<ApiResponse<MenuEntity>> {
        val menuReference = firebaseFirestore.collection("menu").document(menu.id)
        menuReference.set(menu).await()
        emit(ApiResponse.Success(menu))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun editMenu(menu: MenuEntity) = flow<ApiResponse<MenuEntity>> {
        val menuReference = firebaseFirestore.collection("menu").document(menu.id)
        menuReference.set(menu).await()
        emit(ApiResponse.Success(menu))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getMenus() = flow<ApiResponse<List<MenuResponse>>> {
        val menusReference = firebaseFirestore.collection("menu").get().await()
        val menus = menusReference.toObjects(MenuResponse::class.java)
        emit(ApiResponse.Success(menus))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun deleteMenu(menuEntity: MenuEntity) = flow<ApiResponse<MenuEntity>> {
        val menusReference =
            firebaseFirestore.collection("menu").document(menuEntity.id).delete().await()
        emit(ApiResponse.Success(menuEntity))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}