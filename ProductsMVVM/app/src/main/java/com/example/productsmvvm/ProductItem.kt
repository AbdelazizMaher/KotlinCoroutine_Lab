package com.example.productsmvvm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.products.Product

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: Product?, actionType: String, action: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = product?.thumbnail,
                    contentDescription = product?.description,
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp)
                )
                Column(

                ) {
                    Text(text = product!!.title, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(text = product.description, fontSize = 8.sp, fontWeight = FontWeight.Normal)
                }
            }
            Button(
                onClick = action,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF34D399),
                    contentColor = Color.White
                ),
            ) {
                Text(text = actionType)
            }
        }

    }
}