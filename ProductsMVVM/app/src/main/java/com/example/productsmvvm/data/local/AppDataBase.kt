package com.example.productscoroutine.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.products.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getProductsDAO(): ProductsDAO

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {

            return instance ?: synchronized(this) {
                val tempInstance =  Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "products_database"
                ).build()
                instance = tempInstance
                tempInstance
            }
        }
    }
}
