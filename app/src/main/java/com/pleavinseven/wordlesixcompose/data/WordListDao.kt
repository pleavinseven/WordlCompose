package com.pleavinseven.wordlesixcompose.data

import androidx.room.*

@Dao
interface WordListDao {
    @Query("SELECT word FROM wordlist WHERE used = 0 ORDER BY id DESC LIMIT 1")
    suspend fun readWord(): String

//    @Update
//    suspend fun updateWord(word: WordList)
}


