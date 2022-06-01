package com.example.fishmarket.di

import androidx.room.Room
import com.example.fishmarket.data.repository.menu.MenuRepository
import com.example.fishmarket.data.repository.menu.source.local.MenuLocalDataSource
import com.example.fishmarket.data.repository.menu.source.remote.MenuRemoteDataSource
import com.example.fishmarket.data.repository.restaurant.RestaurantRepository
import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.repository.restaurant.source.remote.RestaurantRemoteDataSource
import com.example.fishmarket.data.repository.status_transaction.StatusTransactionRepository
import com.example.fishmarket.data.repository.status_transaction.source.local.LocalStatusTransactionDataSource
import com.example.fishmarket.data.repository.status_transaction.source.remote.StatusTransactionRemoteDataSource
import com.example.fishmarket.data.repository.table.TableRepository
import com.example.fishmarket.data.repository.table.source.local.TableLocalDataSource
import com.example.fishmarket.data.repository.table.source.remote.TableRemoteDataSource
import com.example.fishmarket.data.repository.transaction.TransactionRepository
import com.example.fishmarket.data.repository.transaction.source.local.TransactionLocalDataSource
import com.example.fishmarket.data.repository.transaction.source.remote.TransactionRemoteDataSource
import com.example.fishmarket.data.source.local.FishMarketDatabase
import com.example.fishmarket.domain.repository.*
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<FishMarketDatabase>().restaurantDao() }
    factory { get<FishMarketDatabase>().tableDao() }
    factory { get<FishMarketDatabase>().transactionDao() }
    factory { get<FishMarketDatabase>().statusTransactionDao() }
    factory { get<FishMarketDatabase>().menuDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            FishMarketDatabase::class.java, "fish_market.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }
}

val repositoryModule = module {
    single { RestaurantLocalDataSource(get()) }
    single { RestaurantRemoteDataSource(get()) }
    single { TableLocalDataSource(get()) }
    single { TableRemoteDataSource(get()) }
    single { TransactionLocalDataSource(get()) }
    single { TransactionRemoteDataSource(get()) }
    single { LocalStatusTransactionDataSource(get()) }
    single { StatusTransactionRemoteDataSource(get()) }
    single { MenuLocalDataSource(get())}
    single { MenuRemoteDataSource(get()) }

    single<IRestaurantRepository> { RestaurantRepository(get(), get()) }
    single<ITableRepository> { TableRepository(get(), get()) }
    single<ITransactionRepository> { TransactionRepository(get(), get()) }
    single<IStatusTransactionRepository> { StatusTransactionRepository(get(), get()) }
    single<IMenuRepository> { MenuRepository(get(),get()) }
}