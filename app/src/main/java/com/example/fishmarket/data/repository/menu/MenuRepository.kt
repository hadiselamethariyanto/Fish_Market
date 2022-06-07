package com.example.fishmarket.data.repository.menu

import com.example.fishmarket.data.repository.menu.source.local.MenuLocalDataSource
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.repository.menu.source.remote.MenuRemoteDataSource
import com.example.fishmarket.data.repository.menu.source.remote.model.MenuResponse
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.IMenuRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow

class MenuRepository(
    private val localDataSource: MenuLocalDataSource,
    private val remoteDataSource: MenuRemoteDataSource
) : IMenuRepository {

    override fun insertMenu(menu: MenuEntity): Flow<Resource<MenuEntity>> {
        return object : NetworkBoundInternetOnly<MenuEntity, MenuEntity>() {
            override fun loadFromDB(): Flow<MenuEntity> = localDataSource.getMenu(menu.id)

            override suspend fun createCall(): Flow<ApiResponse<MenuEntity>> =
                remoteDataSource.addMenu(menu)

            override suspend fun saveCallResult(data: MenuEntity) {
                localDataSource.insertMenu(menu)
            }

        }.asFlow()
    }

    override fun editMenu(menu: MenuEntity): Flow<Resource<MenuEntity>> {
        return object : NetworkBoundInternetOnly<MenuEntity, MenuEntity>() {
            override fun loadFromDB(): Flow<MenuEntity> = localDataSource.getMenu(menu.id)

            override suspend fun createCall(): Flow<ApiResponse<MenuEntity>> =
                remoteDataSource.editMenu(menu)

            override suspend fun saveCallResult(data: MenuEntity) {
                localDataSource.updateMenu(menu)
            }

        }.asFlow()
    }

    override fun deleteMenu(menu: MenuEntity): Flow<Resource<List<MenuEntity>>> {
        return object :
            NetworkBoundInternetOnly<List<MenuEntity>, MenuEntity>() {
            override fun loadFromDB(): Flow<List<MenuEntity>> =
                localDataSource.getMenus()

            override suspend fun createCall(): Flow<ApiResponse<MenuEntity>> =
                remoteDataSource.deleteMenu(menu)

            override suspend fun saveCallResult(data: MenuEntity) {
                localDataSource.deleteMenu(data)
            }
        }.asFlow()
    }

    override fun getMenus(): Flow<Resource<List<MenuEntity>>> {
        return object : NetworkBoundResource<List<MenuEntity>, List<MenuResponse>>() {
            override fun loadFromDB(): Flow<List<MenuEntity>> = localDataSource.getMenus()

            override fun shouldFetch(data: List<MenuEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MenuResponse>>> =
                remoteDataSource.getMenus()

            override suspend fun saveCallResult(data: List<MenuResponse>) {
                localDataSource.insertMenus(DataMapper.mapMenuResponseToEntity(data))
            }

        }.asFlow()
    }

}