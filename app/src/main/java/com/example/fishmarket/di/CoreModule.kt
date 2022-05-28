package com.example.fishmarket.di

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.RestaurantRepository
import com.example.fishmarket.data.repository.restaurant.source.local.RestaurantLocalDataSource
import com.example.fishmarket.data.repository.restaurant.source.remote.RestaurantRemoteDataSource
import com.example.fishmarket.data.repository.status_transaction.StatusTransactionRepository
import com.example.fishmarket.data.repository.status_transaction.source.local.LocalStatusTransactionDataSource
import com.example.fishmarket.data.repository.table.TableRepository
import com.example.fishmarket.data.repository.table.source.local.TableLocalDataSource
import com.example.fishmarket.data.repository.transaction.TransactionRepository
import com.example.fishmarket.data.repository.transaction.source.local.TransactionLocalDataSource
import com.example.fishmarket.data.source.local.FishMarketDatabase
import com.example.fishmarket.domain.repository.IRestaurantRepository
import com.example.fishmarket.domain.repository.IStatusTransactionRepository
import com.example.fishmarket.domain.repository.ITableRepository
import com.example.fishmarket.domain.repository.ITransactionRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<FishMarketDatabase>().restaurantDao() }
    factory { get<FishMarketDatabase>().tableDao() }
    factory { get<FishMarketDatabase>().transactionDao() }
    factory { get<FishMarketDatabase>().statusTransactionDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            FishMarketDatabase::class.java, "fish_market.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val icon1 = R.drawable.ic_dibersihkan
                val contentValues = ContentValues()
                contentValues.put("id", 1)
                contentValues.put("name", "dibersihkan")
                contentValues.put("icon", icon1)

                val icon2 = R.drawable.ic_fire_fish
                val cv2 = ContentValues()
                cv2.put("id", 2)
                cv2.put("name", "dibakar")
                cv2.put("icon", icon2)

                val icon3 = R.drawable.ic_served
                val cv3 = ContentValues()
                cv3.put("id", 3)
                cv3.put("name", "disajikan")
                cv3.put("icon", icon3)

                val icon4 = R.drawable.ic_payment
                val cv4 = ContentValues()
                cv4.put("id", 4)
                cv4.put("name", "dibayar")
                cv4.put("icon", icon4)

                db.insert("status_transaction", SQLiteDatabase.CONFLICT_IGNORE, contentValues)
                db.insert("status_transaction", SQLiteDatabase.CONFLICT_IGNORE, cv2)
                db.insert("status_transaction", SQLiteDatabase.CONFLICT_IGNORE, cv3)
                db.insert("status_transaction", SQLiteDatabase.CONFLICT_IGNORE, cv4)
            }
        })
            .fallbackToDestructiveMigration().build()
    }
}

val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }
}

val repositoryModule = module {
    single { RestaurantLocalDataSource(get()) }
    single { RestaurantRemoteDataSource(get()) }
    single { TableLocalDataSource(get()) }
    single { TransactionLocalDataSource(get()) }
    single { LocalStatusTransactionDataSource(get()) }

    single<IRestaurantRepository> { RestaurantRepository(get(), get()) }
    single<ITableRepository> { TableRepository(get()) }
    single<ITransactionRepository> { TransactionRepository(get()) }
    single<IStatusTransactionRepository> { StatusTransactionRepository(get()) }
}