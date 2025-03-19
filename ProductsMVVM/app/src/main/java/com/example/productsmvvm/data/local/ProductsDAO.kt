package com.example.productscoroutine.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.products.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDAO {
    @Query("SELECT * FROM products_table")
    fun  getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product) : Long

    @Delete
    suspend fun deleteProduct(product: Product) : Int
}