package com.example.fishmarket.data.repository.transaction.source.remote.model

data class TransactionResponse(
    var id: String? = "",
    var id_table: String? = "",
    var id_restaurant: String? = "",
    var created_date: Long? = 0,
    var dibakar_date: Long? = 0,
    var disajikan_date: Long? = 0,
    var finished_date: Long? = 0,
    var status: Int? = 0,
    var total_fee: Int? = 0,
    var detail: List<DetailTransactionResponse>? = listOf(),
    var no_urut: Int? = 1
)