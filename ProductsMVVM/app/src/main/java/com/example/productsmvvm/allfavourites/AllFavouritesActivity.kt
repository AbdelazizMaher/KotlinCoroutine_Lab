package com.example.productsmvvm.allfavourites

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.products.Product
import com.example.productscoroutine.db.AppDataBase
import com.example.productscoroutine.network.RetrofitHelper
import com.example.productsmvvm.ProductItem
import com.example.productsmvvm.allproducts.AllProductsFactory
import com.example.productsmvvm.allproducts.AllProductsScreen
import com.example.productsmvvm.allproducts.AllProductsViewModel
import com.example.productsmvvm.allproducts.LoadingIndicator
import com.example.productsmvvm.data.local.ProductsLocalDataSource
import com.example.productsmvvm.data.model.Response
import com.example.productsmvvm.data.remote.ProductsRemoteDataSource
import com.example.productsmvvm.repo.ProductsRepository
import kotlinx.coroutines.launch

class AllFavouritesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllFavouritesScreen(
                viewModel = ViewModelProvider(
                    this,
                    AllFavouritesFactory(
                        ProductsRepository.getInstance(
                        ProductsLocalDataSource(AppDataBase.getInstance(this).getProductsDAO()),
                        ProductsRemoteDataSource(RetrofitHelper.apiService)
                    ))
                )[AllFavouritesViewModel::class.java]
            )
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AllFavouritesScreen(viewModel : AllFavouritesViewModel) {
    val uiState by viewModel.products.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllProducts(false)
    }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.message.collect {message ->
            snackBarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
        }
    }

    when(uiState) {
        is Response.Loading -> {
            LoadingIndicator()
        }

        is Response.Error -> {

        }
        is Response.Success -> {
            val data =  (uiState as Response.Success).data
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackBarHostState) })
            { innerPadding ->
                LazyColumn (modifier = Modifier.fillMaxSize().padding(innerPadding)){
                    items(data.size){
                        ProductItem(data.get(it), "Delete"){
                            viewModel.removeFromFavourites(data.get(it))
                        }
                    }
                }
            }

        }
    }


}