package com.example.fishmarket.domain.model

data class ChangeStatusTransaction(
    var id: String,
    var id_table: String,
    var id_restaurant: String,
    var created_date: Long,
    var dibakar_date: Long,
    var disajikan_date: Long,
    var finished_date: Long,
    var status: Int,
    var total_fee: Int,
    var table_name: String,
    var restaurant_name: String
)