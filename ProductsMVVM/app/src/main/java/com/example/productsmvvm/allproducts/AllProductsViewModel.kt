package com.example.productsmvvm.allproducts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.products.Product
import com.example.productsmvvm.data.model.Response
import com.example.productsmvvm.repo.IProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: IProductsRepository) : ViewModel()  {
    private val mutableProducts = MutableStateFlow<Response>(Response.Loading)
    val products = mutableProducts.asStateFlow()

    private val mutableMessage = MutableSharedFlow<String>()
    val message = mutableMessage.asSharedFlow()

    fun getAllProducts(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repo.getAllProducts(isOnline)
                data
                    .catch {  }
                    .collect { list ->
                    mutableProducts.value = Response.Success(list)
                }
            } catch (e: Exception) {
                mutableMessage.emit("An error occured : ${e.message}")
            }
        }
    }

    fun addToFavourites(product: Product?) {
        if (product != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = repo.insertProduct(product)
                    if (result > 0) {
                        mutableMessage.emit("Added to favourites")
                    }else {
                        mutableMessage.emit("Product is already in favourites")
                    }
                }catch (e: Exception){
                    mutableMessage.emit("An error occured : ${e.message}")
                }
            }
        }
    }
}

class AllProductsFactory(private val repo: IProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllProductsViewModel(repo) as T
    }
}