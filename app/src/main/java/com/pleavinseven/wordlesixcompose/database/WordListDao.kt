package com.pleavinseven.wordlesixcompose.database

import androidx.room.*

@Dao
interface WordListDao {
    @Query("SELECT word FROM wordlist WHERE used = 0 LIMIT 1")
    suspend fun readWord(): String

    @Query("UPDATE wordlist SET used = 1 WHERE word = :usedWord")
    suspend fun updateWord(usedWord: String)
}


