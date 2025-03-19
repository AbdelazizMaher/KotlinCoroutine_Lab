package com.example.productscoroutine.network

import com.example.productsmvvm.data.remote.ProductResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProductList(): Response<ProductResponse>
}