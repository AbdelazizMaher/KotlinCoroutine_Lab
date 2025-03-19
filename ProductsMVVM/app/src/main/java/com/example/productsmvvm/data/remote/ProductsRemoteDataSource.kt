package com.example.productsmvvm.data.remote

import com.example.products.Product
import com.example.productscoroutine.network.ProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductsRemoteDataSource(private val service: ProductService) : IProductsRemoteDataSource {
    override suspend fun getAllProducts(): List<Product>? {
        return service.getProductList().body()?.products
    }
}