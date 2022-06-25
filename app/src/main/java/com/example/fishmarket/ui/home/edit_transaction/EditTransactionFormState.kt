package com.example.fishmarket.ui.home.edit_transaction

data class EditTransactionFormState(
    val priceError: Int? = null,
    val quantityError: Int? = null,
    val isDataValid: Boolean = false
)