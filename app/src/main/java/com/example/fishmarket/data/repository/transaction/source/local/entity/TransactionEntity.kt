package com.example.fishmarket.data.repository.transaction.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "id_table") var id_table: String,
    @ColumnInfo(name = "id_restaurant") var id_restaurant: String,
    @ColumnInfo(name = "created_date") var created_date: Long,
    @ColumnInfo(name = "dibakar_date") var dibakar_date: Long,
    @ColumnInfo(name = "disajikan_date") var disajikan_date: Long,
    @ColumnInfo(name = "finished_date") var finished_date: Long,
    @ColumnInfo(name = "status") var status: Int
)