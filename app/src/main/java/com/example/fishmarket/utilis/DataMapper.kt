package com.example.fishmarket.utilis

import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.remote.model.RestaurantResponse
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.data.repository.status_transaction.source.remote.model.StatusTransactionResponse
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.table.source.remote.model.TableResponse

object DataMapper {

    fun mapRestaurantResponseToEntity(list: List<RestaurantResponse>): List<RestaurantEntity> =
        list.map {
            RestaurantEntity(
                id = it.id ?: "",
                name = it.name ?: "",
                createdDate = it.createdDate ?: 0
            )
        }

    fun mapTableResponseToEntity(list: List<TableResponse>): List<TableEntity> = list.map {
        TableEntity(
            id = it.id ?: "",
            name = it.name ?: "",
            status = it.status ?: false,
            createdDate = it.createdDate ?: 0
        )
    }

    fun mapStatusTransactionResponseToEntity(list: List<StatusTransactionResponse>): List<StatusTransactionEntity> {
        val newList = ArrayList<StatusTransactionEntity>()
        for (x in list) {
            var icon = R.drawable.ic_payment
            when (x.id) {
                1 -> {
                    icon = R.drawable.ic_dibersihkan
                }
                2 -> {
                    icon = R.drawable.ic_fire_fish
                }
                3 -> {
                    icon = R.drawable.ic_served
                }
            }

            newList.add(
                StatusTransactionEntity(
                    id = x.id ?: 0,
                    name = x.name ?: "",
                    icon = icon
                )
            )
        }

        return newList
    }
}