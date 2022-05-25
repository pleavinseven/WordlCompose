package com.example.wordlesixcompose.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wordlesixcompose.data.model.WordList

@Dao
interface WordListDao {
    @Query("SELECT word FROM wordlist WHERE used = 0 ORDER BY id DESC LIMIT 1")
    fun readWord(): LiveData<List<WordList>>

    @Update
    suspend fun updateWord(alarm: WordList)
}


