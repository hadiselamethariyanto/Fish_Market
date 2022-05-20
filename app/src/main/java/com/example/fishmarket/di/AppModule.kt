package com.example.fishmarket.di

import com.example.fishmarket.ui.add_restaurant.AddRestaurantViewModel
import com.example.fishmarket.ui.edit_restaurant.EditRestaurantViewModel
import com.example.fishmarket.ui.restaurant.RestaurantViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { AddRestaurantViewModel(get()) }
    viewModel { RestaurantViewModel(get()) }
    viewModel { EditRestaurantViewModel(get()) }
}