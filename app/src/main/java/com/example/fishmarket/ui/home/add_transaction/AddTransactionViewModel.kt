package com.example.fishmarket.ui.home.add_transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.domain.repository.ICategoryRepository
import com.example.fishmarket.domain.repository.IMenuRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.example.fishmarket.domain.usecase.TableUseCase
import com.example.fishmarket.utilis.Utils

class AddTransactionViewModel(
    private val repository: ITransactionRepository,
    private val tableUseCase: TableUseCase,
    private val menuRepository: IMenuRepository,
    private val categoryRepository: ICategoryRepository
) : ViewModel() {

    private var _table = MutableLiveData<Table>()
    val table: LiveData<Table> get() = _table

    private var _restaurant = MutableLiveData<RestaurantEntity>()
    val restaurant: LiveData<RestaurantEntity> get() = _restaurant

    fun setTable(table: Table) {
        _table.value = table
    }

    fun setRestaurant(restaurant: RestaurantEntity) {
        _restaurant.value = restaurant
    }

    fun resetTransaction() {
        _table.value = Table("", "", false, 0)
        _restaurant.value = RestaurantEntity("", "", 0)
    }

    fun addTransaction(
        totalFee: Int,
        detail: List<DetailTransactionResponse>
    ): LiveData<Resource<TransactionEntity>> {
        val id = Utils.getRandomString()
        val createdDate = System.currentTimeMillis()
        val status = 1
        val idTable = table.value?.id
        val idRestaurant = restaurant.value?.id

        val transaction = TransactionResponse(
            id = id,
            id_table = idTable ?: "",
            id_restaurant = idRestaurant ?: "",
            created_date = createdDate,
            dibakar_date = 0,
            disajikan_date = 0,
            status = status,
            finished_date = 0,
            total_fee = totalFee,
            detail = detail
        )

        return repository.addTransaction(transaction).asLiveData()
    }

    fun getMenus(id: String) = menuRepository.getMenusByCategory(id).asLiveData()

    fun getAvailableTable() = tableUseCase.getAvailableTable().asLiveData()

    fun getCategories() = categoryRepository.getCategories().asLiveData()
}