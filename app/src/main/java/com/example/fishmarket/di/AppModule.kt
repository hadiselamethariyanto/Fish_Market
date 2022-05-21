package com.example.fishmarket.di

import com.example.fishmarket.ui.restaurant.add_restaurant.AddRestaurantViewModel
import com.example.fishmarket.ui.restaurant.edit_restaurant.EditRestaurantViewModel
import com.example.fishmarket.ui.restaurant.list_restaurant.RestaurantViewModel
import com.example.fishmarket.ui.table.add_table.AddTableViewModel
import com.example.fishmarket.ui.table.edit_table.EditTableViewModel
import com.example.fishmarket.ui.table.list_table.TableViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { AddRestaurantViewModel(get()) }
    viewModel { RestaurantViewModel(get()) }
    viewModel { EditRestaurantViewModel(get()) }
    viewModel { AddTableViewModel(get()) }
    viewModel { TableViewModel(get()) }
    viewModel { EditTableViewModel(get()) }
}