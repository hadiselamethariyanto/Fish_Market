package com.example.fishmarket.utilis

import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.repository.category.source.remote.model.CategoryResponse
import com.example.fishmarket.data.repository.login.source.local.entity.UserEntity
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.repository.menu.source.remote.model.MenuResponse
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantWithTransactionEntity
import com.example.fishmarket.data.repository.restaurant.source.remote.model.RestaurantResponse
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.data.repository.status_transaction.source.remote.model.StatusTransactionResponse
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.repository.table.source.remote.model.TableResponse
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionFireEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.domain.model.*
import com.google.firebase.auth.FirebaseUser

object DataMapper {

    fun mapMenuEntitiesToDomain(list: List<MenuEntity>): List<Menu> = list.map {
        Menu(
            id = it.id,
            name = it.name,
            price = it.price,
            unit = it.unit,
            image = it.image,
            id_category = it.id_category,
            created_date = it.created_date
        )
    }

    fun mapMenuEntityToDomain(menuEntity: MenuEntity): Menu =
        Menu(
            id = menuEntity.id,
            name = menuEntity.name,
            price = menuEntity.price,
            unit = menuEntity.unit,
            image = menuEntity.image,
            id_category = menuEntity.id_category,
            created_date = menuEntity.created_date
        )

    fun mapRestaurantWithTransactionEntityToDomain(list: List<RestaurantWithTransactionEntity>): List<RestaurantWithTransaction> {
        return list.map {
            val restaurant = mapRestaurantEntityToDomain(it.restaurant)
            val transaction = mapTransactionFireEntityToDomain(it.transaction)
            RestaurantWithTransaction(restaurant = restaurant, transaction = transaction)
        }
    }

    private fun mapTransactionFireEntityToDomain(list: List<TransactionFireEntity>): List<TransactionFire> =
        list.map {
            TransactionFire(
                id = it.id,
                name = it.name,
                id_restaurant = it.id_restaurant,
                created_date = it.created_date,
                status = it.status
            )
        }

    fun mapRestaurantEntityToDomain(restaurantEntity: RestaurantEntity): Restaurant = Restaurant(
        id = restaurantEntity.id,
        name = restaurantEntity.name,
        createdDate = restaurantEntity.createdDate
    )

    fun mapRestaurantEntitiesToDomain(list: List<RestaurantEntity>): List<Restaurant> =
        list.map { restaurantEntity ->
            Restaurant(
                id = restaurantEntity.id,
                name = restaurantEntity.name,
                createdDate = restaurantEntity.createdDate
            )
        }

    fun mapStatusTransactionEntitiesToDomain(list: List<StatusTransactionEntity>): List<StatusTransaction> =
        list.map {
            StatusTransaction(id = it.id, name = it.name, icon = it.icon)
        }

    fun mapCategoryEntityToDomain(categoryEntity: CategoryEntity) = Category(
        id = categoryEntity.id,
        name = categoryEntity.name,
        created_date = categoryEntity.created_date
    )

    fun mapCategoryEntitiesToDomain(list: List<CategoryEntity>) = list.map { categoryEntity ->
        Category(
            id = categoryEntity.id,
            name = categoryEntity.name,
            created_date = categoryEntity.created_date
        )
    }

    fun mapTableEntitiesToDomain(list: List<TableEntity>): List<Table> = list.map {
        Table(id = it.id, name = it.name, status = it.status, createdDate = it.createdDate)
    }

    fun mapTableEntityToDomain(tableEntity: TableEntity) = Table(
        id = tableEntity.id,
        name = tableEntity.name,
        status = tableEntity.status,
        createdDate = tableEntity.createdDate
    )

    fun mapFirebaseUserToUser(user: FirebaseUser) =
        User(id = user.uid, email = user.email.toString())

    fun mapFirebaseUserToUserEntity(user: FirebaseUser) = UserEntity(
        id = user.uid,
        email = user.email.toString(),
        display_name = user.displayName.toString(),
        photo_url = user.photoUrl.toString()
    )

    fun mapRestaurantResponseToEntity(list: List<RestaurantResponse>): List<RestaurantEntity> =
        list.map {
            RestaurantEntity(
                id = it.id ?: "",
                name = it.name ?: "",
                createdDate = it.createdDate ?: 0
            )
        }

    fun mapTransactionResponseToEntity(transactionResponse: TransactionResponse) =
        TransactionEntity(
            id = transactionResponse.id.toString(),
            id_table = transactionResponse.id_table.toString(),
            id_restaurant = transactionResponse.id_restaurant.toString(),
            created_date = transactionResponse.created_date ?: 0,
            dibakar_date = transactionResponse.dibakar_date ?: 0,
            disajikan_date = transactionResponse.disajikan_date ?: 0,
            finished_date = transactionResponse.finished_date ?: 0,
            status = transactionResponse.status ?: 0,
            total_fee = transactionResponse.total_fee ?: 0
        )

    fun mapCategoriesResponseToEntity(list: List<CategoryResponse>): List<CategoryEntity> =
        list.map {
            CategoryEntity(
                id = it.id.toString(),
                name = it.name.toString(),
                created_date = it.created_date ?: 0
            )
        }

    fun mapTableResponseToEntity(list: List<TableResponse>): List<TableEntity> = list.map {
        TableEntity(
            id = it.id ?: "",
            name = it.name ?: "",
            status = false,
            createdDate = it.createdDate ?: 0
        )
    }

    fun mapTransactionResponseToEntity(list: List<TransactionResponse>): List<TransactionEntity> =
        list.map {
            TransactionEntity(
                id = it.id.toString(),
                id_restaurant = it.id_restaurant.toString(),
                id_table = it.id_table.toString(),
                created_date = it.created_date ?: 0,
                dibakar_date = it.dibakar_date ?: 0,
                disajikan_date = it.disajikan_date ?: 0,
                finished_date = it.finished_date ?: 0,
                status = it.status ?: 0,
                total_fee = it.total_fee ?: 0
            )
        }

    fun mapDetailTransactionsToEntity(
        list: List<DetailTransactionResponse>,
        id: String
    ): List<DetailTransactionEntity> =
        list.map {
            DetailTransactionEntity(
                id = it.id.toString(),
                id_menu = it.id_menu.toString(),
                quantity = it.quantity ?: 0.0,
                price = it.price ?: 0,
                id_transaction = id
            )
        }

    fun mapMenuResponseToEntity(list: List<MenuResponse>): List<MenuEntity> = list.map {
        MenuEntity(
            id = it.id.toString(),
            name = it.name.toString(),
            price = it.price ?: 0,
            unit = it.unit.toString(),
            image = it.image.toString(),
            id_category = it.id_category.toString(),
            created_date = it.created_date ?: 0
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