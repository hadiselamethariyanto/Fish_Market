package com.example.fishmarket.data.repository.transaction.source.local.entity

data class ChangeStatusTransactionEntity(
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
    var restaurant_name: String,
    var no_urut:Int,
    var original_fee:Int,
    var discount:Int
)