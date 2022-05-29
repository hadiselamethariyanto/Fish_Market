package com.example.fishmarket.data.repository.transaction.source.remote.model

data class TransactionResponse(
    var id: String? = null,
    var id_table: String? = null,
    var id_restaurant: String? = null,
    var created_date: Long? = null,
    var dibakar_date: Long? = null,
    var disajikan_date: Long? = null,
    var finished_date: Long? = null,
    var status: Int? = null
)