package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.domain.model.Transaction
import com.example.fishmarket.domain.usecase.restaurant.RestaurantUseCase
import com.example.fishmarket.domain.usecase.status_transaction.StatusTransactionUseCase
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val statusTransactionUseCase: StatusTransactionUseCase,
    private val restaurantUseCase: RestaurantUseCase
) : ViewModel() {

    private var _filter = MutableLiveData(0)
    val filter: LiveData<Int> get() = _filter

    private var _detailTransactionHistory = MutableLiveData<List<DetailTransactionHistory>>()
    val detailTransactionHistory: LiveData<List<DetailTransactionHistory>> get() = _detailTransactionHistory

    val transactions = Transformations.switchMap(filter) { filter ->
        transactionUseCase.getTransactions(filter).asLiveData()
    }

    fun getStatusTransaction() = statusTransactionUseCase.getStatusTransaction().asLiveData()

    fun changeStatusTransaction(
        transaction: TransactionHomeEntity,
        newStatus: Int,
        idRestaurant: String
    ): LiveData<Resource<Transaction>> {
        val dataTransactionUpdate = TransactionEntity(
            id = transaction.id,
            id_table = transaction.id_table,
            id_restaurant = transaction.id_restaurant,
            created_date = transaction.created_date,
            dibakar_date = transaction.dibakar_date,
            disajikan_date = transaction.disajikan_date,
            finished_date = transaction.finished_date,
            status = newStatus,
            total_fee = getTotalFee()
        )

        val finishedDate = System.currentTimeMillis()
        when (newStatus) {
            4 -> {
                dataTransactionUpdate.finished_date = finishedDate
            }
            3 -> {
                if (transaction.status > newStatus) {
                    dataTransactionUpdate.disajikan_date = 0
                }

                dataTransactionUpdate.disajikan_date = finishedDate
            }
            2 -> {
                if (transaction.status > newStatus) {
                    dataTransactionUpdate.finished_date = 0
                    dataTransactionUpdate.disajikan_date = 0
                }

                dataTransactionUpdate.dibakar_date = finishedDate
                dataTransactionUpdate.id_restaurant = idRestaurant
            }
            1 -> {
                if (transaction.status > newStatus) {
                    dataTransactionUpdate.finished_date = 0
                    dataTransactionUpdate.disajikan_date = 0
                    dataTransactionUpdate.dibakar_date = 0
                }
                dataTransactionUpdate.created_date = finishedDate
            }
        }

        val detailTransaction: List<DetailTransactionEntity> =
            _detailTransactionHistory.value?.map {
                DetailTransactionEntity(
                    id = it.id,
                    id_transaction = it.id_transaction,
                    id_menu = it.id_menu,
                    quantity = it.quantity,
                    price = it.price,
                    status = it.status
                )
            } ?: listOf()

        return transactionUseCase.changeStatusTransaction(
            dataTransactionUpdate,
            detailTransaction
        ).asLiveData()
    }

    fun getTotalFee(): Int {
        val newList = _detailTransactionHistory.value?.filter {
            it.status
        } ?: listOf()

        return newList.sumOf { it.price * it.quantity }.toInt()
    }


    fun changeFilter() {
        if (_filter.value == 0) {
            _filter.value = 1
        } else {
            _filter.value = 0
        }
    }

    fun getRestaurant() = restaurantUseCase.getRestaurant().asLiveData()

    fun getDetailTransaction(id: String) = viewModelScope.launch {
        transactionUseCase.getDetailTransaction(id).collect {
            _detailTransactionHistory.value = it
        }
    }

    fun removeItem(position: Int) {
        val list = _detailTransactionHistory.value
        list?.get(position)?.status = false

        _detailTransactionHistory.value = list
    }
}