package com.example.fishmarket.data.repository.menu

import com.example.fishmarket.data.repository.menu.source.local.MenuLocalDataSource
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.repository.menu.source.remote.MenuRemoteDataSource
import com.example.fishmarket.data.repository.menu.source.remote.model.MenuResponse
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.model.Menu
import com.example.fishmarket.domain.repository.IMenuRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuRepository(
    private val localDataSource: MenuLocalDataSource,
    private val remoteDataSource: MenuRemoteDataSource
) : IMenuRepository {

    override fun insertMenu(menu: MenuEntity): Flow<Resource<Menu>> {
        return object : NetworkBoundInternetOnly<Menu, MenuEntity>() {
            override fun loadFromDB(): Flow<Menu> = localDataSource.getMenu(menu.id).map {
                DataMapper.mapMenuEntityToDomain(it)
            }

            override suspend fun createCall(): Flow<ApiResponse<MenuEntity>> =
                remoteDataSource.addMenu(menu)

            override suspend fun saveCallResult(data: MenuEntity) {
                localDataSource.insertMenu(menu)
            }

        }.asFlow()
    }

    override fun editMenu(menu: MenuEntity): Flow<Resource<Menu>> {
        return object : NetworkBoundInternetOnly<Menu, MenuEntity>() {
            override fun loadFromDB(): Flow<Menu> = localDataSource.getMenu(menu.id).map {
                DataMapper.mapMenuEntityToDomain(it)
            }

            override suspend fun createCall(): Flow<ApiResponse<MenuEntity>> =
                remoteDataSource.editMenu(menu)

            override suspend fun saveCallResult(data: MenuEntity) {
                localDataSource.updateMenu(menu)
            }

        }.asFlow()
    }

    override fun deleteMenu(menu: MenuEntity): Flow<Resource<List<Menu>>> {
        return object :
            NetworkBoundInternetOnly<List<Menu>, MenuEntity>() {
            override fun loadFromDB(): Flow<List<Menu>> =
                localDataSource.getMenus().map {
                    DataMapper.mapMenuEntitiesToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<MenuEntity>> =
                remoteDataSource.deleteMenu(menu)

            override suspend fun saveCallResult(data: MenuEntity) {
                localDataSource.deleteMenu(data)
            }
        }.asFlow()
    }

    override fun getMenus(): Flow<Resource<List<Menu>>> {
        return object : NetworkBoundResource<List<Menu>, List<MenuResponse>>() {
            override fun loadFromDB(): Flow<List<Menu>> = localDataSource.getMenus().map {
                DataMapper.mapMenuEntitiesToDomain(it)
            }

            override fun shouldFetch(data: List<Menu>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MenuResponse>>> =
                remoteDataSource.getMenus()

            override suspend fun saveCallResult(data: List<MenuResponse>) {
                localDataSource.insertMenus(DataMapper.mapMenuResponseToEntity(data))
            }

        }.asFlow()
    }

    override fun getMenusByCategory(id: String): Flow<Resource<List<Menu>>> {
        return object : NetworkBoundResource<List<Menu>, List<MenuResponse>>() {
            override fun loadFromDB(): Flow<List<Menu>> =
                localDataSource.getMenusByCategory(id).map {
                    DataMapper.mapMenuEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Menu>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MenuResponse>>> =
                remoteDataSource.getMenus()

            override suspend fun saveCallResult(data: List<MenuResponse>) {
                localDataSource.insertMenus(DataMapper.mapMenuResponseToEntity(data))
            }

        }.asFlow()
    }

}