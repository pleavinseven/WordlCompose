package com.example.wordlesixcompose.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wordlesixcompose.data.model.WordList

@Dao
interface WordListDao {
    @Query("SELECT * FROM wordlist ORDER BY  id ASC")
    fun readWord(): LiveData<List<WordList>>

    @Update
    suspend fun updateWord(alarm: WordList)
}


