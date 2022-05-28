package com.example.fishmarket.data.repository.transaction.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT t.id,tr.name,t.id_restaurant,t.created_date,t.status FROM `transaction` t LEFT JOIN table_restaurant tr ON t.id_table = tr.id WHERE t.status = 2")
data class TransactionFireEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id_restaurant") val id_restaurant: String,
    @ColumnInfo(name = "created_date") val created_date: Long,
    @ColumnInfo(name = "status") val status: Int
)