package com.example.fishmarket.data.repository.restaurant

import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.data.repository.restaurant.source.remote.RestaurantRemoteDataSource
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.IRestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestaurantRepository(
    private val localDataSource: RestaurantLocalDataSource,
    private val remoteDataSource: RestaurantRemoteDataSource
) :
    IRestaurantRepository {

    override fun addRestaurant(restaurant: RestaurantEntity): Flow<Resource<RestaurantEntity>> {
        return object : NetworkBoundInternetOnly<RestaurantEntity, RestaurantEntity>() {
            override fun loadFromDB(): Flow<RestaurantEntity> =
                localDataSource.getRestaurant(restaurant.id)

            override suspend fun createCall(): Flow<ApiResponse<RestaurantEntity>> =
                remoteDataSource.addRestaurant(restaurant)

            override suspend fun saveCallResult(data: RestaurantEntity) {
                localDataSource.addRestaurant(restaurant)
            }
        }.asFlow()
    }

    override fun deleteRestaurant(restaurant: RestaurantEntity): Flow<Resource<List<RestaurantWithTransactionEntity>>> {
        return object :
            NetworkBoundInternetOnly<List<RestaurantWithTransactionEntity>, RestaurantEntity>() {
            override fun loadFromDB(): Flow<List<RestaurantWithTransactionEntity>> =
                localDataSource.getRestaurantWithTransaction()

            override suspend fun createCall(): Flow<ApiResponse<RestaurantEntity>> =
                remoteDataSource.deleteRestaurant(restaurant)

            override suspend fun saveCallResult(data: RestaurantEntity) {
                localDataSource.deleteRestaurant(restaurant)
            }
        }.asFlow()
    }


    override fun updateRestaurant(restaurantEntity: RestaurantEntity): Flow<Resource<RestaurantEntity>> {
        return object : NetworkBoundInternetOnly<RestaurantEntity, RestaurantEntity>() {
            override fun loadFromDB(): Flow<RestaurantEntity> =
                localDataSource.getRestaurant(restaurantEntity.id)

            override suspend fun createCall(): Flow<ApiResponse<RestaurantEntity>> =
                remoteDataSource.updateRestaurant(restaurantEntity)

            override suspend fun saveCallResult(data: RestaurantEntity) {
                localDataSource.updateRestaurant(data)
            }

        }.asFlow()
    }

    override fun getRestaurantWithTransaction(): Flow<List<RestaurantWithTransactionEntity>> =
        localDataSource.getRestaurantWithTransaction()

    override fun getRestaurant(): Flow<List<RestaurantEntity>> = localDataSource.getRestaurant()

    override fun getRestaurant(id: String) = localDataSource.getRestaurant(id)

}