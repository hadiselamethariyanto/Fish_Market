package com.example.fishmarket.data.repository.table.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_restaurant")
data class TableEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "status") var status: Boolean,
    @ColumnInfo(name = "id_transaction") var id_transaction:String,
    @ColumnInfo(name = "createdDate") var createdDate: Long
)