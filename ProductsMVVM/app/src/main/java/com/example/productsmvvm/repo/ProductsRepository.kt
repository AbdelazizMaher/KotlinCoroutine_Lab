package com.example.productsmvvm.repo

import com.example.products.Product
import com.example.productsmvvm.data.local.IProductsLocalDataSource
import com.example.productsmvvm.data.remote.IProductsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductsRepository private constructor(
    private val localDataSource: IProductsLocalDataSource,
    private val remoteDataSource: IProductsRemoteDataSource
)  : IProductsRepository {

    companion object {
        @Volatile
        private var instance: ProductsRepository? = null
        fun getInstance(
            localDataSource: IProductsLocalDataSource,
            remoteDataSource: IProductsRemoteDataSource
        ): ProductsRepository {
            return instance ?: synchronized(this) {
                val tempInstance = ProductsRepository(localDataSource, remoteDataSource)
                instance = tempInstance
                tempInstance
            }
        }
    }

    override suspend fun getAllProducts(isOnline: Boolean): Flow<List<Product>> {
        return if (isOnline) {
            flowOf(remoteDataSource.getAllProducts()!!)

        } else {
            localDataSource.getAllProducts()
        }
    }

    override suspend fun insertProduct(product: Product): Long {
        return localDataSource.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product): Int {
        return localDataSource.deleteProduct(product)
    }
}