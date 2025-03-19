package com.example.productsmvvm.data.local

import com.example.products.Product
import com.example.productscoroutine.db.ProductsDAO
import kotlinx.coroutines.flow.Flow

class ProductsLocalDataSource(private val dao: ProductsDAO) : IProductsLocalDataSource {

    override fun getAllProducts(): Flow<List<Product>> {
        return dao.getAllProducts()
    }

    override suspend fun insertProduct(product: Product) : Long {
        return dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product) : Int {
        return dao.deleteProduct(product)
    }
}