package com.example.productsmvvm.allproducts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.products.Product
import com.example.productscoroutine.db.AppDataBase
import com.example.productscoroutine.network.RetrofitHelper
import com.example.productsmvvm.ProductItem
import com.example.productsmvvm.allproducts.ui.theme.ProductsMVVMTheme
import com.example.productsmvvm.data.local.ProductsLocalDataSource
import com.example.productsmvvm.data.model.Response
import com.example.productsmvvm.data.remote.ProductsRemoteDataSource
import com.example.productsmvvm.repo.ProductsRepository
import kotlinx.coroutines.launch

class AllProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllProductsScreen(
                viewModel = ViewModelProvider(
                    this,
                    AllProductsFactory(ProductsRepository.getInstance(
                        ProductsLocalDataSource(AppDataBase.getInstance(this).getProductsDAO()),
                        ProductsRemoteDataSource(RetrofitHelper.apiService)
                    ))
                )[AllProductsViewModel::class.java]
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize()
    ) {
        CircularProgressIndicator()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AllProductsScreen(viewModel : AllProductsViewModel) {
    val uiState by viewModel.products.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllProducts(true)
    }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.message.collect {message ->
            scope.launch {
                snackBarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
            }
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
                        ProductItem(data[it], "Favourite"){
                            viewModel.addToFavourites(data[it])
                        }
                    }
                }
            }

        }
    }

}





