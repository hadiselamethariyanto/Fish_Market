package com.example.fishmarket.data.repository.restaurant.source.remote

import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.remote.model.RestaurantResponse
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class RestaurantRemoteDataSource(private val firebase: FirebaseFirestore) {

    suspend fun addRestaurant(restaurant: RestaurantEntity) = flow<ApiResponse<RestaurantEntity>> {
        val restaurantReference = firebase.collection("restaurant").document(restaurant.id)
        restaurantReference.set(restaurant).await()
        emit(ApiResponse.Success(restaurant))
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun updateRestaurant(restaurant: RestaurantEntity) =
        flow<ApiResponse<RestaurantEntity>> {
            val restaurantReference = firebase.collection("restaurant").document(restaurant.id)
            restaurantReference.set(restaurant).await()
            emit(ApiResponse.Success(restaurant))
        }.catch {
            emit(ApiResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    suspend fun deleteRestaurant(restaurant: RestaurantEntity) =
        flow<ApiResponse<RestaurantEntity>> {
            val restaurantReference = firebase.collection("restaurant").document(restaurant.id)
            restaurantReference.delete().await()
            emit(ApiResponse.Success(restaurant))
        }.catch {
            emit(ApiResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun getRestaurant() = flow<ApiResponse<List<RestaurantResponse>>> {
        val restaurantReference = firebase.collection("restaurant").get().await()
        val restaurants = restaurantReference.toObjects(RestaurantResponse::class.java)
        emit(ApiResponse.Success(restaurants))
    }

}