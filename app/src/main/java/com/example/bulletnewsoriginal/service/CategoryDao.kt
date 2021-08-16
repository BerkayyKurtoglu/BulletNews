package com.example.bulletnewsoriginal.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bulletnewsoriginal.model.CategoryDatabaseItem

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category : CategoryDatabaseItem)

    @Query("DELETE FROM categoriesDatabase WHERE category = :category")
    suspend fun deleteCategory(category : String)

    @Query("SELECT * FROM categoriesDatabase ORDER BY id ASC")
    suspend fun getAllCategories() : List<CategoryDatabaseItem>

}