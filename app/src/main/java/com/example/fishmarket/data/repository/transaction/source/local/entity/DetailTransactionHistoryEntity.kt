package com.example.fishmarket.data.repository.transaction.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT dt.id, dt.id_transaction,m.name,dt.quantity,dt.price,m.unit,dt.status,m.id as id_menu from detail_transaction dt INNER JOIN menu m ON dt.id_menu = m.id")
data class DetailTransactionHistoryEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "id_menu") var id_menu: String,
    @ColumnInfo(name = "id_transaction") var id_transaction: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "quantity") var quantity: Double,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "unit") var unit: String,
    @ColumnInfo(name = "status") var status: Boolean
)