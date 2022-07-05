package com.pleavinseven.wordlesixcompose.database.repository

import com.pleavinseven.wordlesixcompose.database.WordListDao

class WordRepository(private val wordListDao: WordListDao) {

    suspend fun readWord(): String {
        return wordListDao.readWord()
    }

    suspend fun updateWord(word: String) {
        return wordListDao.updateWord(word)
    }
}