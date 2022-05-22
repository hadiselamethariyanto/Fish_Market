package com.example.fishmarket.data.repository.restaurant.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionFireEntity

data class RestaurantWithTransactionEntity(
    @Embedded val restaurant: RestaurantEntity,
    @Relation(
        parentColumn = "id",
        entity = TransactionFireEntity::class,
        entityColumn = "id_restaurant"
    )
    val transaction: List<TransactionFireEntity>
)