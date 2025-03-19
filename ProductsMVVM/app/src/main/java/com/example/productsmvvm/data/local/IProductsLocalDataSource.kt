package com.example.productsmvvm.data.local

import androidx.lifecycle.LiveData
import com.example.products.Product
import kotlinx.coroutines.flow.Flow

interface IProductsLocalDataSource {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun insertProduct(product: Product) : Long
    suspend fun deleteProduct(product: Product) : Int
}