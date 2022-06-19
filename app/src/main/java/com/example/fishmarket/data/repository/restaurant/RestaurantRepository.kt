package com.example.fishmarket.data.repository.restaurant

import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.remote.RestaurantRemoteDataSource
import com.example.fishmarket.data.repository.restaurant.source.remote.model.RestaurantResponse
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.domain.model.RestaurantWithTransaction
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestaurantRepository(
    private val localDataSource: RestaurantLocalDataSource,
    private val remoteDataSource: RestaurantRemoteDataSource
) :
    IRestaurantRepository {

    override fun addRestaurant(restaurant: RestaurantEntity): Flow<Resource<Restaurant>> {
        return object : NetworkBoundInternetOnly<Restaurant, RestaurantEntity>() {
            override fun loadFromDB(): Flow<Restaurant> =
                localDataSource.getRestaurant(restaurant.id).map {
                    DataMapper.mapRestaurantEntityToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<RestaurantEntity>> =
                remoteDataSource.addRestaurant(restaurant)

            override suspend fun saveCallResult(data: RestaurantEntity) {
                localDataSource.addRestaurant(restaurant)
            }
        }.asFlow()
    }

    override fun deleteRestaurant(restaurant: RestaurantEntity): Flow<Resource<List<RestaurantWithTransaction>>> {
        return object :
            NetworkBoundInternetOnly<List<RestaurantWithTransaction>, RestaurantEntity>() {
            override fun loadFromDB(): Flow<List<RestaurantWithTransaction>> =
                localDataSource.getRestaurantWithTransaction().map {
                    DataMapper.mapRestaurantWithTransactionEntityToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<RestaurantEntity>> =
                remoteDataSource.deleteRestaurant(restaurant)

            override suspend fun saveCallResult(data: RestaurantEntity) {
                localDataSource.deleteRestaurant(restaurant)
            }
        }.asFlow()
    }


    override fun updateRestaurant(restaurantEntity: RestaurantEntity): Flow<Resource<Restaurant>> {
        return object : NetworkBoundInternetOnly<Restaurant, RestaurantEntity>() {
            override fun loadFromDB(): Flow<Restaurant> =
                localDataSource.getRestaurant(restaurantEntity.id).map {
                    DataMapper.mapRestaurantEntityToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<RestaurantEntity>> =
                remoteDataSource.updateRestaurant(restaurantEntity)

            override suspend fun saveCallResult(data: RestaurantEntity) {
                localDataSource.updateRestaurant(data)
            }

        }.asFlow()
    }

    override fun getRestaurantWithTransaction(): Flow<Resource<List<RestaurantWithTransaction>>> {
        return object :
            NetworkBoundResource<List<RestaurantWithTransaction>, List<RestaurantResponse>>() {
            override fun loadFromDB(): Flow<List<RestaurantWithTransaction>> =
                localDataSource.getRestaurantWithTransaction().map {
                    DataMapper.mapRestaurantWithTransactionEntityToDomain(it)
                }

            override fun shouldFetch(data: List<RestaurantWithTransaction>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<RestaurantResponse>>> =
                remoteDataSource.getRestaurant()

            override suspend fun saveCallResult(data: List<RestaurantResponse>) {
                localDataSource.addRestaurants(DataMapper.mapRestaurantResponseToEntity(data))
            }

        }.asFlow()
    }

    override fun getRestaurant(): Flow<Resource<List<Restaurant>>> {
        return object : NetworkBoundResource<List<Restaurant>, List<RestaurantResponse>>() {
            override fun loadFromDB(): Flow<List<Restaurant>> =
                localDataSource.getRestaurant().map {
                    DataMapper.mapRestaurantEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Restaurant>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<RestaurantResponse>>> =
                remoteDataSource.getRestaurant()

            override suspend fun saveCallResult(data: List<RestaurantResponse>) {
                localDataSource.addRestaurants(DataMapper.mapRestaurantResponseToEntity(data))
            }

        }.asFlow()
    }

    override fun getRestaurant(id: String) =
        localDataSource.getRestaurant(id).map { DataMapper.mapRestaurantEntityToDomain(it) }

}