package com.example.fishmarket

import android.app.Application
import com.example.fishmarket.di.*
import com.example.fishmarket.utilis.ModelCart
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.Forest.plant


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
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    viewModelModule,
                    firebaseModule,
                    useCaseModule
                )
            )
        }
    }

    fun getCart(): ModelCart {
        return myCart
    }
}