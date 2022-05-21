package com.example.fishmarket.di

import androidx.room.Room
import com.example.fishmarket.data.repository.restaurant.RestaurantRepository
import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.repository.table.TableRepository
import com.example.fishmarket.data.repository.table.source.local.TableLocalDataSource
import com.example.fishmarket.data.source.local.FishMarketDatabase
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.repository.ITableRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<FishMarketDatabase>().restaurantDao() }
    factory { get<FishMarketDatabase>().tableDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            FishMarketDatabase::class.java, "fish_market.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { RestaurantLocalDataSource(get()) }
    single { TableLocalDataSource(get()) }
    single<IRestaurantRepository> { RestaurantRepository(get()) }
    single<ITableRepository> { TableRepository(get()) }
}