package com.example.fishmarket.ui.home.edit_transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fishmarket.R

class EditTransactionViewModel : ViewModel() {
    private val _formEditTransaction = MutableLiveData<EditTransactionFormState>()
    val formEditTransactionFormState: LiveData<EditTransactionFormState> get() = _formEditTransaction

    fun formDataChanged(price: String, quantity: String) {
        if (!isEditFieldValueValid(price)) {
            _formEditTransaction.value =
                EditTransactionFormState(priceError = R.string.warning_price_menu_empty)
        } else if (!isEditFieldValueValid(quantity)) {
            _formEditTransaction.value =
                EditTransactionFormState(quantityError = R.string.warning_quantity_empty)
        } else {
            _formEditTransaction.value = EditTransactionFormState(isDataValid = true)
        }
    }

    private fun isEditFieldValueValid(value: String): Boolean {
        return value.isNotEmpty()
    }
}