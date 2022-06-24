package com.example.fishmarket.ui.menu.add_menu

data class MenuFormState(
    val nameError: Int? = null,
    val priceError: Int? = null,
    val categoryError: Int? = null,
    val unit: Int? = null,
    val isDataValid: Boolean = false
)