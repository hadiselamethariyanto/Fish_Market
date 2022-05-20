package com.example.fishmarket.di

import androidx.room.Room
import com.example.fishmarket.data.repository.restaurant.RestaurantRepository
import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.source.local.FishMarketDatabase
import com.example.fishmarket.domain.repository.IRestaurantRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<FishMarketDatabase>().restaurantDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FishMarketDatabase::class.java, "fish_market.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { RestaurantLocalDataSource(get()) }
    single<IRestaurantRepository> { RestaurantRepository(get()) }
}