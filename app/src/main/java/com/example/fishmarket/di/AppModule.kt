package com.example.fishmarket.di

import com.example.fishmarket.domain.usecase.LoginInteractor
import com.example.fishmarket.domain.usecase.LoginUseCase
import com.example.fishmarket.ui.history.list_history.HistoryViewModel
import com.example.fishmarket.ui.home.transaction.HomeViewModel
import com.example.fishmarket.ui.home.add_transaction.AddTransactionViewModel
import com.example.fishmarket.ui.login.LoginViewModel
import com.example.fishmarket.ui.main.MainViewModel
import com.example.fishmarket.ui.menu.MenuViewModel
import com.example.fishmarket.ui.menu.add_category.AddCategoryViewModel
import com.example.fishmarket.ui.menu.add_menu.AddMenuViewModel
import com.example.fishmarket.ui.menu.edit_category.EditCategoryViewModel
import com.example.fishmarket.ui.menu.list_category.ListCategoryViewModel
import com.example.fishmarket.ui.menu.list_menu.ListMenuViewModel
import com.example.fishmarket.ui.report.ReportViewModel
import com.example.fishmarket.ui.restaurant.add_restaurant.AddRestaurantViewModel
import com.example.fishmarket.ui.restaurant.edit_restaurant.EditRestaurantViewModel
import com.example.fishmarket.ui.restaurant.list_restaurant.RestaurantViewModel
import com.example.fishmarket.ui.settings.SettingsViewModel
import com.example.fishmarket.ui.table.add_table.AddTableViewModel
import com.example.fishmarket.ui.table.edit_table.EditTableViewModel
import com.example.fishmarket.ui.table.list_table.TableViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val useCaseModule = module {
    factory<LoginUseCase> { LoginInteractor(get()) }
}

val viewModelModule = module {
    viewModel { AddRestaurantViewModel(get()) }
    viewModel { RestaurantViewModel(get()) }
    viewModel { EditRestaurantViewModel(get()) }
    viewModel { AddTableViewModel(get()) }
    viewModel { TableViewModel(get()) }
    viewModel { EditTableViewModel(get()) }
    viewModel { AddTransactionViewModel(get(), get(), get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { MenuViewModel(get()) }
    viewModel { AddMenuViewModel(get(), get()) }
    viewModel { ListMenuViewModel(get()) }
    viewModel { AddCategoryViewModel(get()) }
    viewModel { EditCategoryViewModel(get()) }
    viewModel { ListCategoryViewModel(get()) }
    viewModel { ReportViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}