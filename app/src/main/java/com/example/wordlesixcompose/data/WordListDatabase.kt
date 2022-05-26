package com.example.wordlesixcompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wordlesixcompose.data.model.WordList


@Database(entities = [WordList::class], version = 1)
abstract class WordListDatabase : RoomDatabase() {

    abstract fun wordlistDao(): WordListDao

    companion object {
        @Volatile
        var INSTANCE: WordListDatabase? = null

        fun getDatabase(context: Context): WordListDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordListDatabase::class.java,
                    "word_list_database"
                ).createFromAsset(
                    "database/word_list.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

