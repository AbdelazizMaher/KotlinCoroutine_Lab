package com.example.products

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey var id: String,
    var title: String,
    var description: String,
    var thumbnail: String
) : Serializable {
}

