package com.example.productsmvvm.repo

import com.example.products.Product
import com.example.productsmvvm.data.local.IProductsLocalDataSource
import com.example.productsmvvm.data.remote.IProductsRemoteDataSource
import kotlinx.coroutines.flow.Flow

interface IProductsRepository {
    suspend fun getAllProducts(isOnline: Boolean): Flow<List<Product>>
    suspend fun insertProduct(product: Product) : Long
    suspend fun deleteProduct(product: Product) : Int

}