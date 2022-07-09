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
import com.example.fishmarket.data.repository.transaction.source.local.entity.*
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.repository.transaction.source.remote.model.TransactionResponse
import com.example.fishmarket.domain.model.*
import com.google.firebase.auth.FirebaseUser

object DataMapper {

    fun mapRestaurantTransactionToDomain(list: List<RestaurantTransactionEntity>): List<RestaurantTransaction> =
        list.map {
            RestaurantTransaction(
                id = it.id,
                name = it.name,
                income = it.income,
                transactionCount = it.transactionCount
            )
        }

    fun mapTransactionsWithDetailEntityToDomain(list: List<TransactionWithDetailEntity>): List<TransactionWithDetail> =
        list.map {
            val transaction = mapTransactionEntityToDomain(it.transactionEntity)
            val detailTransactionHistory =
                mapDetailTransactionHistoryEntitiesToDomain(it.detailTransactionEntity)

            TransactionWithDetail(
                transaction = transaction,
                detailTransactionHistory = detailTransactionHistory
            )
        }

    fun mapTransactionWithDetailEntityToDomain(data: TransactionWithDetailEntity?): TransactionWithDetail {
        val transaction = mapTransactionEntityToDomain(
            data?.transactionEntity ?: TransactionEntity(
                "",
                "",
                "",
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            )
        )
        val detailTransactionHistory =
            mapDetailTransactionHistoryEntitiesToDomain(data?.detailTransactionEntity ?: listOf())
        return TransactionWithDetail(
            transaction = transaction,
            detailTransactionHistory = detailTransactionHistory
        )
    }

    fun mapProductToDetailResponse(list: List<Product>): List<DetailTransactionResponse> =
        list.map { menu ->
            DetailTransactionResponse(
                id = Utils.getRandomString(),
                id_menu = menu.id,
                quantity = menu.quantity,
                price = menu.price,
                status = true
            )
        }

    fun mapDetailTransactionHistoryToDetailResponse(list: List<DetailTransactionHistory>): List<DetailTransactionResponse> =
        list.map { menu ->
            DetailTransactionResponse(
                id = menu.id,
                id_menu = menu.id_menu,
                quantity = menu.quantity,
                price = menu.price,
                status = menu.status
            )
        }

    fun mapCombineDetailTransaction(
        listExisting: ArrayList<DetailTransactionResponse>,
        newList: ArrayList<DetailTransactionResponse>
    ): List<DetailTransactionResponse> {
        val combinedDetail = ArrayList<DetailTransactionResponse>()
        newList.map { new ->
            val sa = listExisting.filter { x -> x.id_menu == new.id_menu }
            if (sa.size == 1) {
                val newQuantity = sa[0].quantity!! + new.quantity!!
                sa[0].quantity = newQuantity
                combinedDetail.add(sa[0])
            } else {
                combinedDetail.add(new)
            }
        }
        return combinedDetail
    }

    fun mapTransactionToTransactionResponse(
        transaction: Transaction,
        totalFee: Int,
        detail: List<DetailTransactionResponse>
    ): TransactionResponse =
        TransactionResponse(
            id = transaction.id,
            id_table = transaction.id_table,
            id_restaurant = transaction.id_restaurant,
            created_date = transaction.created_date,
            dibakar_date = transaction.dibakar_date,
            disajikan_date = transaction.disajikan_date,
            finished_date = transaction.finished_date,
            status = transaction.status,
            total_fee = totalFee,
            detail = detail,
            no_urut = transaction.no_urut
        )

    fun mapDetailTransactionHistoryToProduct(list: List<DetailTransactionHistory>): List<Product> =
        list.map {
            Product(
                id = it.id_menu,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                unit = it.unit
            )
        }

    fun mapDetailTransactionHistoryEntitiesToDomain(list: List<DetailTransactionHistoryEntity>): List<DetailTransactionHistory> =
        list.map {
            DetailTransactionHistory(
                id = it.id,
                id_transaction = it.id_transaction,
                name = it.name,
                quantity = it.quantity,
                price = it.price,
                unit = it.unit,
                status = it.status,
                id_menu = it.id_menu
            )
        }

    fun mapTransactionHomeEntitiesToDomain(list: List<TransactionHomeEntity>): List<TransactionHome> =
        list.map {
            TransactionHome(
                id = it.id,
                table = it.table,
                id_table = it.id_table,
                id_restaurant = it.id_restaurant,
                restaurant = it.restaurant,
                created_date = it.created_date,
                dibakar_date = it.dibakar_date,
                disajikan_date = it.disajikan_date,
                finished_date = it.finished_date,
                status = it.status,
                total_fee = it.total_fee,
                no_urut = it.no_urut
            )
        }

    fun mapTransactionEntitiesToDomain(list: List<TransactionEntity>): List<Transaction> =
        list.map { transactionEntity ->
            Transaction(
                id = transactionEntity.id,
                id_table = transactionEntity.id_table,
                id_restaurant = transactionEntity.id_restaurant,
                created_date = transactionEntity.created_date,
                dibakar_date = transactionEntity.dibakar_date,
                disajikan_date = transactionEntity.disajikan_date,
                finished_date = transactionEntity.finished_date,
                status = transactionEntity.status,
                total_fee = transactionEntity.total_fee,
                no_urut = transactionEntity.no_urut
            )
        }


    fun mapChangeStatusTransactionEntityToDomain(value: ChangeStatusTransactionEntity): ChangeStatusTransaction =
        ChangeStatusTransaction(
            id = value.id,
            id_table = value.id_table,
            id_restaurant = value.id_restaurant,
            created_date = value.created_date,
            dibakar_date = value.dibakar_date,
            disajikan_date = value.disajikan_date,
            finished_date = value.finished_date,
            status = value.status,
            total_fee = value.total_fee,
            table_name = value.table_name,
            restaurant_name = value.restaurant_name,
            no_urut = value.no_urut,
            original_fee = value.original_fee,
            discount = value.discount
        )

    fun mapTransactionEntityToDomain(transactionEntity: TransactionEntity): Transaction =
        Transaction(
            id = transactionEntity.id,
            id_table = transactionEntity.id_table,
            id_restaurant = transactionEntity.id_restaurant,
            created_date = transactionEntity.created_date,
            dibakar_date = transactionEntity.dibakar_date,
            disajikan_date = transactionEntity.disajikan_date,
            finished_date = transactionEntity.finished_date,
            status = transactionEntity.status,
            total_fee = transactionEntity.total_fee,
            no_urut = transactionEntity.no_urut
        )

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
        Table(
            id = it.id,
            name = it.name,
            status = it.status,
            createdDate = it.createdDate,
            idTransaction = it.id_transaction
        )
    }

    fun mapTableEntityToDomain(tableEntity: TableEntity) = Table(
        id = tableEntity.id,
        name = tableEntity.name,
        status = tableEntity.status,
        createdDate = tableEntity.createdDate,
        idTransaction = tableEntity.id_transaction
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
            total_fee = transactionResponse.total_fee ?: 0,
            no_urut = transactionResponse.no_urut ?: 0,
            original_fee = transactionResponse.original_fee ?: 0,
            discount = transactionResponse.discount ?: 0
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
            createdDate = it.createdDate ?: 0,
            id_transaction = it.idTransaction ?: ""
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
                total_fee = it.total_fee ?: 0,
                no_urut = it.no_urut ?: 1,
                original_fee = it.original_fee ?: 0,
                discount = it.discount ?: 0
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
                id_transaction = id,
                status = it.status ?: false
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