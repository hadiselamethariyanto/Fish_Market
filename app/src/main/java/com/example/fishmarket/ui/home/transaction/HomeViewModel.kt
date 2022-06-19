package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.*
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.example.fishmarket.domain.usecase.status_transaction.StatusTransactionUseCase

class HomeViewModel(
    private val transactionRepository: ITransactionRepository,
    private val statusTransactionUseCase: StatusTransactionUseCase,
    private val restaurantRepository: IRestaurantRepository
) : ViewModel() {

    private var _filter = MutableLiveData(0)
    val filter: LiveData<Int> get() = _filter

    val transactions = Transformations.switchMap(filter) { filter ->
        transactionRepository.getTransactions(filter).asLiveData()
    }

    fun getStatusTransaction() = statusTransactionUseCase.getStatusTransaction().asLiveData()

    fun changeStatusTransaction(
        transaction: TransactionHomeEntity,
        newStatus: Int,
        idRestaurant: String
    ): LiveData<Resource<TransactionEntity>> {
        val dataTransactionUpdate = TransactionEntity(
            id = transaction.id,
            id_table = transaction.id_table,
            id_restaurant = transaction.id_restaurant,
            created_date = transaction.created_date,
            dibakar_date = transaction.dibakar_date,
            disajikan_date = transaction.disajikan_date,
            finished_date = transaction.finished_date,
            status = newStatus,
            total_fee = transaction.total_fee
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

        return transactionRepository.changeStatusTransaction(dataTransactionUpdate).asLiveData()
    }


    fun changeFilter() {
        if (_filter.value == 0) {
            _filter.value = 1
        } else {
            _filter.value = 0
        }
    }

    fun getRestaurant() = restaurantRepository.getRestaurant().asLiveData()

    fun getDetailTransaction(id: String) =
        transactionRepository.getDetailTransaction(id).asLiveData()
}