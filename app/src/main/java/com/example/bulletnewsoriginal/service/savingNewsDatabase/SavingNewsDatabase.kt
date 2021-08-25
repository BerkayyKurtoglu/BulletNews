package com.example.bulletnewsoriginal.service.savingNewsDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bulletnewsoriginal.model.Article

@Database(entities = [Article::class],version = 1)
abstract class SavingNewsDatabase : RoomDatabase() {

    abstract fun accessSavingNewsDao() : SavingNewsDao

    companion object{
        @Volatile private var instance : SavingNewsDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this){
            createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,SavingNewsDatabase::class.java,"savingnewsdatabase"
        ).build()

    }

}