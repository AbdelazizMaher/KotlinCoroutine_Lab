package com.example.productsmvvm.data.remote

import com.example.products.Product
import kotlinx.coroutines.flow.Flow

interface IProductsRemoteDataSource {
    suspend fun getAllProducts(): List<Product>?
}