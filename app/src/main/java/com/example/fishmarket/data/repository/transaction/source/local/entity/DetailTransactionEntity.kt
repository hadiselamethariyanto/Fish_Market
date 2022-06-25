package com.example.fishmarket.data.repository.transaction.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_transaction")
data class DetailTransactionEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "id_transaction") var id_transaction: String,
    @ColumnInfo(name = "id_menu") var id_menu: String,
    @ColumnInfo(name = "quantity") var quantity: Double,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "status") var status: Boolean
)