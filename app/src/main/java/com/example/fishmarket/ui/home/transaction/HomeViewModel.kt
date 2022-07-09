package com.example.fishmarket.ui.home.transaction

import androidx.lifecycle.*
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.ChangeStatusTransaction
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.domain.usecase.restaurant.RestaurantUseCase
import com.example.fishmarket.domain.usecase.status_transaction.StatusTransactionUseCase
import com.example.fishmarket.domain.usecase.transaction.TransactionUseCase
import com.example.fishmarket.ui.payment.AddDiscountFormState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val statusTransactionUseCase: StatusTransactionUseCase,
    private val restaurantUseCase: RestaurantUseCase
) : ViewModel() {

    private var _filter = MutableLiveData(0)
    val filter: LiveData<Int> get() = _filter

    private var _discount = MutableLiveData(0)
    val discount: LiveData<Int> get() = _discount

    private var _detailTransactionHistory = MutableLiveData<List<DetailTransactionHistory>>()
    val detailTransactionHistory: LiveData<List<DetailTransactionHistory>> get() = _detailTransactionHistory

    private var _addDiscountForm = MutableLiveData<AddDiscountFormState>()
    val addDiscountFormState: LiveData<AddDiscountFormState> get() = _addDiscountForm

    val transactions = Transformations.switchMap(filter) { filter ->
        transactionUseCase.getTransactions(filter).asLiveData()
    }

    fun addDiscountDataChanged(discount: String, originalFee: Int) {
        if (!isDiscountValid(discount)) {
            _addDiscountForm.value =
                AddDiscountFormState(discountError = R.string.warning_discount_empty)
        } else if (!isDiscountValid(discount, originalFee)) {
            _addDiscountForm.value =
                AddDiscountFormState(discountError = R.string.warning_discount_more)
        } else {
            _addDiscountForm.value = AddDiscountFormState(isDataValid = true)
        }
    }

    fun setDiscount(discount: Int) {
        _discount.value = discount
    }

    fun resetDiscount() {
        _discount.value = 0
        _addDiscountForm.value = AddDiscountFormState(discountError = null, isDataValid = false)
    }

    private fun isDiscountValid(discount: String): Boolean = discount.isNotEmpty()

    private fun isDiscountValid(discount: String, originalFee: Int): Boolean =
        discount.toInt() in 1..originalFee

    fun getStatusTransaction() = statusTransactionUseCase.getStatusTransaction().asLiveData()

    fun changeStatusTransaction(
        transaction: TransactionHomeEntity,
        newStatus: Int,
        idRestaurant: String
    ): LiveData<Resource<ChangeStatusTransaction>> {
        val dataTransactionUpdate = TransactionEntity(
            id = transaction.id,
            id_table = transaction.id_table,
            id_restaurant = transaction.id_restaurant,
            created_date = transaction.created_date,
            dibakar_date = transaction.dibakar_date,
            disajikan_date = transaction.disajikan_date,
            finished_date = transaction.finished_date,
            status = newStatus,
            total_fee = getTotalFee(),
            no_urut = transaction.no_urut,
            original_fee = getOriginalFee(),
            discount = discount.value ?: 0
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

    fun getOriginalFee(): Int {
        val newList = _detailTransactionHistory.value?.filter {
            it.status
        } ?: listOf()

        return newList.sumOf { it.price * it.quantity }.toInt()
    }

    fun getTotalFee(): Int {
        val newList = _detailTransactionHistory.value?.filter {
            it.status
        } ?: listOf()

        return newList.sumOf { it.price * it.quantity }.toInt() - discount.value!!
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