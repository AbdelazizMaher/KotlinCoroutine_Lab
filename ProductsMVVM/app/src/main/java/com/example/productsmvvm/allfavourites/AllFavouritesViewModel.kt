package com.example.productsmvvm.allfavourites

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
import kotlinx.coroutines.launch

class AllFavouritesViewModel(private val repo: IProductsRepository) : ViewModel() {
    private val mutableFavourites = MutableStateFlow<Response>(Response.Loading)
    val products = mutableFavourites.asStateFlow()

    private val mutableMessage = MutableSharedFlow<String>()
    val message = mutableMessage.asSharedFlow()

    fun getAllProducts(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllProducts(isOnline)
                result.collect { list ->
                    mutableFavourites.value = Response.Success(list)
                }
            }catch (e: Exception) {
                mutableMessage.emit("An error occured : ${e.message}")
            }
        }
    }

    fun removeFromFavourites(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.deleteProduct(product)
                if (result > 0) {
                    mutableMessage.emit("Removed from favourites")
                    getAllProducts(false)
                } else {
                    mutableMessage.emit("Product is not in favourites")
                }
            } catch (e: Exception) {
                mutableMessage.emit("An error occured : ${e.message}")
            }
        }
    }

}

class AllFavouritesFactory(private val repo: IProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllFavouritesViewModel(repo) as T
    }
}