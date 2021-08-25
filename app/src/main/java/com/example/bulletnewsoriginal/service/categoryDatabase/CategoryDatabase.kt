package com.example.bulletnewsoriginal.service.categoryDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bulletnewsoriginal.model.CategoryDatabaseItem

@Database(entities = [CategoryDatabaseItem::class],version = 1)
abstract class CategoryDatabase : RoomDatabase(){

    abstract fun categoryDao() : CategoryDao

    companion object{

        @Volatile private var categoryDatabase : CategoryDatabase? = null

        operator fun invoke(context: Context) = categoryDatabase ?: synchronized(this){

            categoryDatabase ?: createDatabase(context = context).also {
                categoryDatabase = it
            }

        }

        private fun createDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext, CategoryDatabase::class.java,"categorydatabase")
            .build()
    }

}