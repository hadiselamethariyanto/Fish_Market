package com.example.fishmarket

import android.app.Application
import com.example.fishmarket.di.databaseModule
import com.example.fishmarket.di.firebaseModule
import com.example.fishmarket.di.repositoryModule
import com.example.fishmarket.di.viewModelModule
import com.example.fishmarket.utilis.ModelCart
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    private var myCart: ModelCart = ModelCart()

    companion object {
        private lateinit var app: App

        fun getApp(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    viewModelModule,
                    firebaseModule
                )
            )
        }
    }

    fun getCart(): ModelCart {
        return myCart
    }
}