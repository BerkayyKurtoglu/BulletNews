package com.example.bulletnewsoriginal.service.savingNewsDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bulletnewsoriginal.model.Article

@Dao
interface SavingNewsDao {

    @Insert
    suspend fun insertToDatabase(article : Article)

    @Query("SELECT * FROM article")
    suspend fun getAllSavedNews() : List<Article>

    @Query("DELETE FROM article WHERE id = :id")
    suspend fun deleteCertainNews(id : Int)

    @Query("DELETE FROM article")
    suspend fun deleteEverything()

}