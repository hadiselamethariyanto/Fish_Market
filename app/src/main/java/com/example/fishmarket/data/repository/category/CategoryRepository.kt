package com.example.fishmarket.data.repository.category

import com.example.fishmarket.data.repository.category.source.local.CategoryLocalDataSource
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.repository.category.source.remote.CategoryRemoteDataSource
import com.example.fishmarket.data.repository.category.source.remote.model.CategoryResponse
import com.example.fishmarket.data.source.remote.NetworkBoundInternetOnly
import com.example.fishmarket.data.source.remote.NetworkBoundResource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.data.source.remote.network.ApiResponse
import com.example.fishmarket.domain.repository.ICategoryRepository
import com.example.fishmarket.utilis.DataMapper
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource
) : ICategoryRepository {

    override fun insertCategory(categoryEntity: CategoryEntity): Flow<Resource<CategoryEntity>> {
        return object : NetworkBoundInternetOnly<CategoryEntity, CategoryEntity>() {
            override fun loadFromDB(): Flow<CategoryEntity> =
                localDataSource.getCategory(categoryEntity.id)

            override suspend fun createCall(): Flow<ApiResponse<CategoryEntity>> =
                remoteDataSource.addCategory(categoryEntity)

            override suspend fun saveCallResult(data: CategoryEntity) {
                localDataSource.addCategory(categoryEntity)
            }

        }.asFlow()
    }

    override fun updateCategory(categoryEntity: CategoryEntity): Flow<Resource<CategoryEntity>> {
        return object : NetworkBoundInternetOnly<CategoryEntity, CategoryEntity>() {
            override fun loadFromDB(): Flow<CategoryEntity> =
                localDataSource.getCategory(categoryEntity.id)

            override suspend fun createCall(): Flow<ApiResponse<CategoryEntity>> =
                remoteDataSource.updateCategory(categoryEntity)

            override suspend fun saveCallResult(data: CategoryEntity) {
                localDataSource.editCategory(data)
            }

        }.asFlow()
    }

    override fun deleteCategory(categoryEntity: CategoryEntity): Flow<Resource<List<CategoryEntity>>> {
        return object : NetworkBoundInternetOnly<List<CategoryEntity>, CategoryEntity>() {
            override fun loadFromDB(): Flow<List<CategoryEntity>> =
                localDataSource.getCategories()

            override suspend fun createCall(): Flow<ApiResponse<CategoryEntity>> =
                remoteDataSource.deleteCategory(categoryEntity)

            override suspend fun saveCallResult(data: CategoryEntity) {
                localDataSource.deleteCategory(data)
            }

        }.asFlow()
    }

    override fun getCategories(): Flow<Resource<List<CategoryEntity>>> {
        return object : NetworkBoundResource<List<CategoryEntity>, List<CategoryResponse>>() {
            override fun loadFromDB(): Flow<List<CategoryEntity>> = localDataSource.getCategories()

            override fun shouldFetch(data: List<CategoryEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CategoryResponse>>> =
                remoteDataSource.getCategories()

            override suspend fun saveCallResult(data: List<CategoryResponse>) {
                localDataSource.addCategories(DataMapper.mapCategoriesResponseToEntity(data))
            }

        }.asFlow()
    }

    override fun getCategory(id: String): Flow<Resource<CategoryEntity>> {
        return object : NetworkBoundResource<CategoryEntity, List<CategoryResponse>>() {
            override fun loadFromDB(): Flow<CategoryEntity> = localDataSource.getCategory(id)

            override fun shouldFetch(data: CategoryEntity?): Boolean = data == null

            override suspend fun createCall(): Flow<ApiResponse<List<CategoryResponse>>> =
                remoteDataSource.getCategories()

            override suspend fun saveCallResult(data: List<CategoryResponse>) {
                localDataSource.addCategories(DataMapper.mapCategoriesResponseToEntity(data))
            }

        }.asFlow()
    }


}