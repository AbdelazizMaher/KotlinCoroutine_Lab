package com.example.productsmvvm.data.model

import com.example.products.Product

sealed class Response {
    data object Loading : Response()
    data class Success(val data: List<Product>) : Response()
    data class Error(val message: String) : Response()
}