package com.example.sharedflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.sharedflow.ui.theme.SharedFlowTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedSearchBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedSearchBar() {
    val names = listOf(
        "Abdelaziz Maher Abdelaziz",
        "Abdelrahman Atia Abdelrahman",
        "Abdelrahman Kamel Fathy",
        "Abram Morris Helmy",
        "Adham Mohamed Hassan Abdou Hassan",
        "Ahmed Mohamed Saad Mohamed Routil",
        "Ahmed Mohamed Saber",
        "Ali Kotb Mohamed Ali",
        "Aliaa Mohamed Ahmed",
        "Ayat Gamal Mustafa",
        "Eman Mahmoud Hanafi",
        "Habiba Mohamed Elhadi Mohamed",
        "Jailan Medhat Mohamed Metwallyy",
        "Malak Raaof Rizkallah Saad",
        "Mario Abdel-Masih Ramzy",
        "Menna Kamal Mohi El Din Ghalwash",
        "Mohamed Tag Eldeen Ahmed Metwally",
        "Nermeen Mohamed Elsayed",
        "Nour Mohamed Agami",
        "Nourhan Essam Eldean",
        "Youssef Abdelkader Said Allam",
        "Youssef Adel Mohamed Ali Fayad",
        "Youssif Nasser Mostafa"
    )

    val searchFlow = remember {MutableSharedFlow<String>()}

    var query = remember { mutableStateOf("") }
    var filteredNames = remember { mutableStateOf(emptyList<String>()) }

    LaunchedEffect(Unit) {
        searchFlow.collect { text ->
            filteredNames.value = names.filter { it.contains(text, ignoreCase = true) }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
            query = query.value,
            onQueryChange = { text ->
                query.value = text
                CoroutineScope(Dispatchers.Main).launch { searchFlow.emit(text) }
            },
            onSearch = {

            },
            active = false,
            onActiveChange = { },
        ) {
        }
        filteredNames.value.forEach { name ->
            Text(text = name, modifier = Modifier.padding(8.dp))
        }
    }
}


