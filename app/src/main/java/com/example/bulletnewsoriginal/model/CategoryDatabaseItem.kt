package com.example.bulletnewsoriginal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoriesDatabase")
data class CategoryDatabaseItem(
    var category: String
){
    @PrimaryKey
    var id : Int = 0

    fun updateClass(exactCategory : String){
        category = exactCategory
    }
}