package com.example.wordlesixcompose.data.repository

import androidx.lifecycle.LiveData
import com.example.wordlesixcompose.data.WordListDao
import com.example.wordlesixcompose.data.model.WordList

class WordRepository(private val wordListDao: WordListDao) {

    val readWordData: LiveData<String> = wordListDao.readWord()

    fun readWord(): LiveData<String> {
       return wordListDao.readWord()
    }
}