package com.example.fishmarket

import android.app.Application
import com.example.fishmarket.di.databaseModule
import com.example.fishmarket.di.firebaseModule
import com.example.fishmarket.di.repositoryModule
import com.example.fishmarket.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
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
}