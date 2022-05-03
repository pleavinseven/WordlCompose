package com.example.wordlesixcompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wordlesixcompose.database.model.WordList


@Database(entities = [WordList::class], version = 2)
abstract class WordListDatabase : RoomDatabase() {

    abstract fun wordlistDao(): WordListDao

    companion object {
        @Volatile
        var INSTANCE: WordListDatabase? = null

        fun getDatabase(context: Context): WordListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordListDatabase::class.java,
                    "word_list_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

