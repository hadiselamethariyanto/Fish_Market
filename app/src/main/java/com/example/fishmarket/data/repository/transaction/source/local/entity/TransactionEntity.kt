package com.example.fishmarket.data.repository.transaction.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "id_table") val id_table: Int,
    @ColumnInfo(name = "id_restaurant") val id_restaurant: Int,
    @ColumnInfo(name = "created_date") val created_date: Long,
    @ColumnInfo(name = "finished_date") val finished_date: Long,
    @ColumnInfo(name = "status") val status: Int
)