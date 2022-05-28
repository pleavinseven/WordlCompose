package com.example.wordlesixcompose.data.repository

import com.example.wordlesixcompose.data.WordListDao

class WordRepository(private val wordListDao: WordListDao) {

    suspend fun readWord(): String {
        return wordListDao.readWord()
    }
}