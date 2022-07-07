package com.example.fishmarket.data.repository.transaction.source.remote.model

data class DetailTransactionResponse(
    val id: String? = "",
    val id_menu: String? = "",
    var quantity: Double? = 0.0,
    val price: Int? = 0,
    val status: Boolean? = true
)